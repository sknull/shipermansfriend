package de.visualdigits.shipermansfriend.data.repository

import co.touchlab.kermit.Logger
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.configuration.keyfactory.BooleanEnum
import de.visualdigits.common.domain.model.errorhandling.Result
import de.visualdigits.common.domain.model.geodata.BoundingBox
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.model.geodata.toLocation
import de.visualdigits.common.domain.model.platform.ConnectivityMode
import de.visualdigits.common.presentation.components.ConnectivityManager
import de.visualdigits.shipermansfriend.data.model.aisstreamio.AisMessage
import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.PositionAisMessageData
import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.SafetyAisMessageData
import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.StaticDataAisMessageData
import de.visualdigits.shipermansfriend.data.model.aisstreamio.status.AisStreamStatus
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.AisStreamState
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.ApiKey
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.ReceivingDataState
import de.visualdigits.shipermansfriend.domain.model.geodata.AisData
import de.visualdigits.shipermansfriend.domain.model.geodata.PositionData
import de.visualdigits.shipermansfriend.domain.model.geodata.SafetyData
import de.visualdigits.shipermansfriend.domain.model.geodata.mmsi.MasterData
import de.visualdigits.shipermansfriend.domain.model.settings.SK
import de.visualdigits.shipermansfriend.domain.repository.LocationProvider
import de.visualdigits.shipermansfriend.domain.repository.SettingsRepository
import de.visualdigits.shipermansfriend.domain.util.formatDistance
import de.visualdigits.shipermansfriend.domain.util.notBlank
import de.visualdigits.shipermansfriend.domain.util.parseDistance
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.wss
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.utils.io.CancellationException
import io.ktor.websocket.Frame
import io.ktor.websocket.readBytes
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.jetbrains.annotations.VisibleForTesting
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
class AisStreamClient(
    private val httpClient: HttpClient,
    private val settingsRepository: SettingsRepository,
    private val locationProvider: LocationProvider,
    private val connectivityManager: ConnectivityManager,
    private val scope: CoroutineScope,
) {

    companion object {

        private val MAX_INACTIVITY_DURATION: Duration = 30.seconds
        private const val HOST_URI = "wss://stream.aisstream.io/v0/stream"
        private const val THRESHOLD_METERS = 500.0

        private val aisJson = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            encodeDefaults = true
        }
    }

    private var streamJob: Job? = null
    private var locationJob: Job? = null
    private var initializerJob: Job? = null
    private var activeApiKey: ApiKey? = null

    private val messageChannel = Channel<AisData>(Channel.BUFFERED)

    val _location = MutableStateFlow<Location?>(null)

    private val startTime = KmpOffsetDateTime.now()

    private val _lastLocationUpdate = MutableStateFlow(startTime)
    val _lastLocationUpdateDuration = MutableStateFlow(0.seconds)

    private val _lastMessageUpdate = MutableStateFlow(startTime)
    private val _lastMessageUpdateDuration = MutableStateFlow(0.seconds)

    private val _previousConnectivityMode = MutableStateFlow(ConnectivityMode.disconnected)
    val _connectivityMode = MutableStateFlow(ConnectivityMode.disconnected)

    val _aisStreamState = MutableStateFlow(AisStreamState.Down)

    val _receivingDataState = MutableStateFlow(ReceivingDataState.noData)

    val _innerRadius = MutableStateFlow(1000.0)

    val _outerBoundingBox = MutableStateFlow<BoundingBox?>(null)

    val _innerBoundingBox = MutableStateFlow<BoundingBox?>(null)

    private val retryCount = MutableStateFlow(0)

    val messages: Flow<AisData> = messageChannel
        .receiveAsFlow()
        .shareIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            replay = 0
        )

    init {
        // monitor connectivity and aisstream.io state using inofficial api
        scope.launch {
            while (isActive) {
                _previousConnectivityMode.update { _connectivityMode.value }
                _connectivityMode.update { connectivityManager.connectivityMode() }
                if (_connectivityMode.value != ConnectivityMode.disconnected) {
                    val aisStreamState = withContext(Dispatchers.IO) {
                        aisStreamState()?.state ?: AisStreamState.Down
                    }
                    _aisStreamState.update { aisStreamState }

                    // when the connected media has changed we need to reconnect to the service
                    if (_connectivityMode.value != _previousConnectivityMode.value && _connectivityMode.value != ConnectivityMode.disconnected) {
                        Logger.i("Internet connection available again - trying to reconnect")
                        activeApiKey
                            ?.also { ak -> start(apiKey = ak, force = true) }
                            ?: start(force = true)
                    }
                } else {
                    Logger.w("Internet connection lost")
                    _receivingDataState.update { ReceivingDataState.disconnected }
                }
                val now = KmpOffsetDateTime.now()
                _lastLocationUpdateDuration.update { now.minus(_lastLocationUpdate.value) }
                val lastMessageUpdateDuration = now.minus(_lastMessageUpdate.value)
                if (lastMessageUpdateDuration > MAX_INACTIVITY_DURATION) {
                    Logger.w("No message received since ${lastMessageUpdateDuration.inWholeMinutes} minutes - assuming connection is lost")
                    _receivingDataState.update { ReceivingDataState.disconnected }
                }
                _lastMessageUpdateDuration.update { lastMessageUpdateDuration }

                delay(10.seconds)
            }
        }

        // monitor receiving state
        scope.launch {
            _receivingDataState
                .transformLatest { state ->
                    if (state == ReceivingDataState.receivingData) {
                        delay(5.seconds)
                        emit(ReceivingDataState.noData)
                    }
                }
                .collect { resetValue ->
                    _receivingDataState.value = resetValue
                }
        }
    }

    fun start(force: Boolean = false) {
        initializerJob?.cancel()
        locationJob?.cancel()
        locationJob = null

        // Using a SupervisorJob ensures that child failures do not kill the app scope
        initializerJob = scope.launch(SupervisorJob() + Dispatchers.Default) {
            var settingsReady = false
            while (!settingsReady) {
                val settingsResult = settingsRepository.getSettings()
                if (settingsResult is Result.Success && settingsResult.data != null) {
                    settingsReady = true
                    val settings = settingsResult.data!!
                    val savedKey = settings.get<String>(SK.aisstreamApiKey)
                    val useGpsLocation = settings.get<BooleanEnum>(SK.useGpsLocation)?.booleanValue ?: false
                    val outerRadius = settings.get<String>(SK.radiusOuter)?.notBlank()?.parseDistance() ?: 2000.0
                    _innerRadius.update { settings.get<String>(SK.radiusInner)?.notBlank()?.parseDistance() ?: 1000.0 }
                    val dbLocation = settings.get<String>(SK.location)?.notBlank()?.toLocation()

                    if (savedKey?.isNotBlank() == true) {
                        if (useGpsLocation) {
                            Logger.i("GPS active. Starting location observation...")

                            val fallbackJob = launch {
                                delay(4.seconds)
                                if (_location.value == null && dbLocation != null) {
                                    Logger.w("GPS delayed. Using initial database location fallback.")
                                    processNewLocation(
                                        targetLocation = dbLocation,
                                        savedKey = savedKey,
                                        outerRadius = outerRadius,
                                        innerRadius = _innerRadius.value,
                                        useGpsLocation = false,
                                        force = force
                                    )
                                }
                            }

                            // Launch the infinite loop completely decoupled on the parent scope
                            locationJob = scope.launch(SupervisorJob() + Dispatchers.Default) {
                                locationProvider.observeLocation().collect { currentGpsLocation ->
                                    fallbackJob.cancel()
                                    processNewLocation(
                                        targetLocation = currentGpsLocation,
                                        savedKey = savedKey,
                                        outerRadius = outerRadius,
                                        innerRadius = _innerRadius.value,
                                        useGpsLocation = true,
                                        force = force
                                    )
                                }
                            }
                        } else {
                            if (dbLocation != null) {
                                processNewLocation(
                                    targetLocation = dbLocation,
                                    savedKey = savedKey,
                                    outerRadius = outerRadius,
                                    innerRadius = _innerRadius.value,
                                    useGpsLocation = false,
                                    force = force
                                )
                            }
                        }
                    }
                } else {
                    delay(1.seconds)
                }
            }
        }
    }

    suspend fun aisStreamState(): AisStreamStatus? {
        return try {
            val json = httpClient
                .get("https://aisuptime.buttermilkgreen.fyi/api/v1/status")
                .bodyAsText()
            aisJson.decodeFromString(AisStreamStatus.serializer(), json)
        } catch (e: Exception) {
            Logger.i("Could not fetch service status",e)
            null
        }
    }

    private fun processNewLocation(
        targetLocation: Location,
        savedKey: String,
        outerRadius: Double,
        innerRadius: Double,
        useGpsLocation: Boolean,
        force: Boolean = false
    ) {
        if (useGpsLocation && _location.value != null && (_location.value?.let { l -> targetLocation.distanceTo(l) } ?: 0.0) < THRESHOLD_METERS) {
            return
        }

        _innerBoundingBox.update { targetLocation.calculateBoundingBox(innerRadius) }
        val outerBoundingBox = targetLocation.calculateBoundingBox(outerRadius)
        _outerBoundingBox.update { outerBoundingBox }
        val apiKey = ApiKey(
            apiKey = savedKey,
            boundingBoxes = outerBoundingBox.toList(),
        )

        _location.update { targetLocation }
        _lastLocationUpdate.update { KmpOffsetDateTime.now() }

        Logger.i("location updated: ${targetLocation.toDmsString()}")
        Logger.i("outerRadius: ${outerRadius.formatDistance()}")
        Logger.i("innerRadius: ${innerRadius.formatDistance()}")
        Logger.i("outerBoundingBox: $outerBoundingBox")
        Logger.i("innerBoundingBox: ${_innerBoundingBox.value}")
        Logger.i("Starting ais client for new bounding box")

        start(apiKey, force)
    }

    @VisibleForTesting
    fun start(apiKey: ApiKey, force: Boolean = false) {
        if (!force && activeApiKey == apiKey && streamJob?.isActive == true) {
            Logger.i("Client is running already with same parameters.")
        }

        activeApiKey = apiKey
        stop()

        // CRITICAL FIX: We add SupervisorJob() and a custom CoroutineExceptionHandler
        // to catch the JobCancellationException on network cancellation safely!
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            if (throwable !is CancellationException) {
                Logger.e("WebSocket exception occurred", throwable)
            } else {
                Logger.e("Unknown error occurred", throwable)
            }
        }

        streamJob = scope.launch(SupervisorJob() + Dispatchers.IO + exceptionHandler) {
            try {
                httpClient.wss(urlString = HOST_URI) {
                    // do not receive organizational or binary messages
                    val authJson = aisJson.encodeToString(apiKey.copy(filterMessageTypes = MessageType.MESSSAGES_OF_INTEREST))
                    send(Frame.Text(authJson))

                    for (frame in incoming) {
                        if (frame is Frame.Binary) {
                            try {
                                val jsonString = frame.readBytes().decodeToString()
                                val message = aisJson.decodeFromString<AisMessage>(jsonString)
                                _lastMessageUpdate.update { KmpOffsetDateTime.now() }
                                _receivingDataState.update { ReceivingDataState.receivingData }
                                _aisStreamState.update { AisStreamState.Up }
                                val aisData = when (message.data) {
                                    is StaticDataAisMessageData -> {
                                        MasterData(
                                            messageType = message.messageType,
                                            name = message.metaData.shipName.trim(),
                                            mmsi = message.metaData.mmsi,
                                            timeUtc = KmpOffsetDateTime.fromString(message.metaData.timeUtc),
                                            imoNumber = message.data.imoNumber,
                                            callSign = message.data.callSign,
                                            destination = message.data.destination,
                                            totalWidth = message.data.dimension.totalWidth,
                                            totalLength = message.data.dimension.totalLength,
                                            shipType = message.data.shipType,
                                            maximumStaticDraught = message.data.maximumStaticDraught
                                        )
                                    }
                                    is PositionAisMessageData -> {
//                                        Logger.i("PositionAisMessageData: $message")
                                        PositionData(
                                            messageType = message.messageType,
                                            name = message.metaData.shipName.trim(),
                                            mmsi = message.metaData.mmsi,
                                            timeUtc = KmpOffsetDateTime.fromString(message.metaData.timeUtc),
                                            location = message.data.location,
                                            sog = message.data.sog,
                                            heading = message.data.displayHeading,
                                            rateOfTurnDegreesPerMinute = message.data.rateOfTurnDegreesPerMinute,
                                            navigationalStatus = message.data.navigationalStatus
                                        )
                                    }
                                    is SafetyAisMessageData -> {
                                        SafetyData(
                                            messageType = message.messageType,
                                            messageId = message.data.messageId,
                                            repeatIndicator = message.data.repeatIndicator,
                                            mmsi = message.data.mmsi,
                                            location = Location(
                                                latitude = message.metaData.latitude,
                                                longitude = message.metaData.longitude
                                            ),
                                            valid = message.data.valid,
                                            text = message.data.text
                                        )
                                    }
                                    else -> AisData(
                                        messageType = message.messageType,
                                        name = message.metaData.shipName.trim(),
                                        mmsi = message.metaData.mmsi,
                                        timeUtc = KmpOffsetDateTime.fromString(message.metaData.timeUtc),
                                    )
                                }
                                // Use trySend to avoid blocking inside the synchronized loop
                                aisData.also { ad ->
                                    messageChannel.trySend(ad)
                                }
                            } catch (e: Exception) {
                                Logger.e("Parsing-Error", e)
                            }
                        }
                    }
                }
            } catch (_: CancellationException) {
                // DO NOT close the messageChannel inside finally or catch!
                // Just log that the switch happened as intended
                Logger.i("WebSocket tunnel safely migrated to new coordinates.")
            } catch (e: Exception) {
                _receivingDataState.update { ReceivingDataState.disconnected }
                Logger.e("Connection error: ${e.message}")
            }
        }
    }

    fun stop() {
        streamJob?.cancel()
        streamJob = null
    }
}
