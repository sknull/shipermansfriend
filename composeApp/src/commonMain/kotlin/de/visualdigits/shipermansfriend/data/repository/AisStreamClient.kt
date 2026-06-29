package de.visualdigits.shipermansfriend.data.repository

import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.configuration.keyfactory.BooleanEnum
import de.visualdigits.common.domain.model.errorhandling.LogMessage.Companion.log
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
import de.visualdigits.shipermansfriend.data.model.aisstreamio.status.ServiceState
import de.visualdigits.shipermansfriend.data.model.aisstreamio.status.ServiceStatus
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.ApiKey
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType
import de.visualdigits.shipermansfriend.domain.model.geodata.AisData
import de.visualdigits.shipermansfriend.domain.model.geodata.MasterData
import de.visualdigits.shipermansfriend.domain.model.geodata.PositionData
import de.visualdigits.shipermansfriend.domain.model.geodata.ReceiverState
import de.visualdigits.shipermansfriend.domain.model.geodata.SafetyData
import de.visualdigits.shipermansfriend.domain.model.settings.SK
import de.visualdigits.shipermansfriend.domain.repository.LocationProvider
import de.visualdigits.shipermansfriend.domain.repository.SettingsRepository
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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
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

        private val MAX_INACTIVITY_MINUTES: Duration = 5.minutes
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
    private var lastStreamingLocation: Location? = null

    private val messageChannel = Channel<AisData>(Channel.BUFFERED)

    private val _location = MutableStateFlow<Location?>(null)
    val location = _location.asStateFlow()

    private val _lastMessageUpdate = MutableStateFlow(KmpOffsetDateTime.now())

    private val _lastLocationUpdate = MutableStateFlow(KmpOffsetDateTime.now())
    private val _lastLocationUpdateMinutes = MutableStateFlow<Long>(0)
    val lastLocationUpdateMinutes = _lastLocationUpdateMinutes.asStateFlow()

    val _receiverState = MutableStateFlow(ReceiverState.noData)
    val receiverState: StateFlow<ReceiverState> = _receiverState.asStateFlow()

    val _innerRadius = MutableStateFlow(1000.0)
    val innerRadius: StateFlow<Double> = _innerRadius.asStateFlow()

    val _outerBoundingBox = MutableStateFlow<BoundingBox?>(null)
    val _innerBoundingBox = MutableStateFlow<BoundingBox?>(null)

    private val _serviceState = MutableStateFlow<ServiceState?>(null)
    val serviceState = _serviceState.asStateFlow()

    private val retryCount = MutableStateFlow(0)

    val messages: Flow<AisData> = messageChannel
        .receiveAsFlow()
        .shareIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            replay = 0
        )

    init {
        scope.launch {
            while (isActive) {
                val minutesSinceLastMessage = KmpOffsetDateTime.now().minus(_lastMessageUpdate.value)
                if (minutesSinceLastMessage > MAX_INACTIVITY_MINUTES && receiverState.value == ReceiverState.noData) {
                    log(Severity.Info, "No messages for $minutesSinceLastMessage minutes. Checking Health Endpoint...", withTag = "AIS")
                    if (_serviceState.value == ServiceState.Down) {
                        log(Severity.Warn, "Health api reported server down", withTag = "AIS")
                        _receiverState.update { ReceiverState.serverDown }
                    }
                }

                delay(30.seconds)
            }
        }

        // monitor service state using inofficial api
        scope.launch {
            while (isActive) {
                val serviceStatus = withContext(Dispatchers.IO) {
                    serviceStatus()
                }
                _serviceState.update { serviceStatus?.state }

                delay(10.seconds)
            }
        }

        // monitor connection and manage reconnection
        scope.launch {
            _receiverState
                .transformLatest { state ->
                    if (state == ReceiverState.connectionLost) {
                        log(Severity.Info, "Connection lost", withTag = "AIS")
                        delay(state.delayUntilNextState)
                        emit(ReceiverState.entries[state.ordinal + 1])
                    } else if (ReceiverState.retryStates.contains(state)) {
                        log(Severity.Info, "Retry to connect attempt ${state.name.takeLast(1)}/7", withTag = "AIS")
                        if (retryCount.value < 3) {
                            if (connectivityManager.connectivityMode() != ConnectivityMode.disconnected) {
                                log(Severity.Info, "ConnectivityManager reports connectivity is back - starting client", withTag = "AIS")
                                start()
                            } else {
                                log(Severity.Info, "Waiting for next attempt [${state.delayUntilNextState.inWholeSeconds} seconds]", withTag = "AIS")
                                retryCount.update { current -> current + 1 }
                                delay(state.delayUntilNextState)
                                emit(ReceiverState.entries[state.ordinal + 1])
                            }
                        } else {
                            log(Severity.Info, "Reconnect attempts exhausted - finally giving up", withTag = "AIS")
                            emit(ReceiverState.cannotRecoverConnection)
                        }
                    } else if (state != ReceiverState.cannotRecoverConnection && state != ReceiverState.serverDown) {
                        delay(state.delayUntilNextState)
                        emit(ReceiverState.noData)
                    }
                }
                .collect { resetValue ->
                    _receiverState.value = resetValue
                }
        }
    }

    fun start() {
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
                    val outerRadius = settings.get<String>(SK.radiusOuter)?.toDoubleOrNull()
                    _innerRadius.update { settings.get<String>(SK.radiusInner)?.toDouble() ?: 1000.0 }
                    val dbLocation = settings.get<String>(SK.location)?.toLocation()

                    if (savedKey?.isNotBlank() == true && outerRadius != null && innerRadius != null) {
                        if (useGpsLocation) {
                            log(Severity.Info, "GPS active. Starting location observation...", withTag = "AIS")

                            val fallbackJob = launch {
                                delay(4.seconds)
                                if (lastStreamingLocation == null && dbLocation != null) {
                                    log(Severity.Warn, "GPS delayed. Using initial database location fallback.", withTag = "AIS")
                                    processNewLocation(
                                        targetLocation = dbLocation,
                                        savedKey = savedKey,
                                        outerRadius = outerRadius,
                                        innerRadius = _innerRadius.value,
                                        useGpsLocation = false
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
                                        useGpsLocation = true
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
                                    useGpsLocation = false
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

    suspend fun serviceStatus(): ServiceStatus? {
        return try {
            val json = httpClient
                .get("https://aisuptime.buttermilkgreen.fyi/api/v1/status")
                .bodyAsText()
            aisJson.decodeFromString(ServiceStatus.serializer(), json)
        } catch (e: Exception) {
            log(Severity.Info, "Could not fetch service status",e, withTag = "AIS")
            null
        }
    }

    private fun processNewLocation(
        targetLocation: Location,
        savedKey: String,
        outerRadius: Double,
        innerRadius: Double,
        useGpsLocation: Boolean
    ) {
        val lastLoc = lastStreamingLocation

        if (useGpsLocation && lastLoc != null && targetLocation.distanceTo(lastLoc) < THRESHOLD_METERS) {
            return
        }

        lastStreamingLocation = targetLocation
        _innerBoundingBox.value = targetLocation.calculateBoundingBox(innerRadius)
        val outerBoundingBox = targetLocation.calculateBoundingBox(outerRadius)
        _outerBoundingBox.value = outerBoundingBox
        val apiKey = ApiKey(
            apiKey = savedKey,
            boundingBoxes = outerBoundingBox.toList(),
        )
        _location.value = targetLocation
        val now = KmpOffsetDateTime.now()
        _lastLocationUpdateMinutes.value = now.minus(_lastLocationUpdate.value).inWholeMinutes
        _lastLocationUpdate.value = now

        log(Severity.Info, "location updated: ${targetLocation.toDmsString()}", withTag = "AIS")
        log(Severity.Info, "outerRadius: $outerRadius", withTag = "AIS")
        log(Severity.Info, "innerRadius: $innerRadius", withTag = "AIS")
        log(Severity.Info, "outerBoundingBox: $outerBoundingBox", withTag = "AIS")
        log(Severity.Info, "innerBoundingBox: ${_innerBoundingBox.value}", withTag = "AIS")
        log(Severity.Info, "Starting ais client for new bounding box", withTag = "AIS")

        start(apiKey)
    }

    fun start(apiKey: ApiKey) {
        if (activeApiKey == apiKey && streamJob?.isActive == true) {
            log(Severity.Info, "Client is running already with same parameters.", withTag = "AIS")
        }

        activeApiKey = apiKey
        stop()

        // CRITICAL FIX: We add SupervisorJob() and a custom CoroutineExceptionHandler
        // to catch the JobCancellationException on network cancellation safely!
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            if (throwable !is CancellationException) {
                log(Severity.Error, "WebSocket exception occurred", throwable, withTag = "AIS")
            } else {
                log(Severity.Error, "Unknown error occurred", throwable, withTag = "AIS")
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
                                        PositionData(
                                            messageType = message.messageType,
                                            name = message.metaData.shipName.trim(),
                                            mmsi = message.metaData.mmsi,
                                            timeUtc = KmpOffsetDateTime.fromString(message.metaData.timeUtc),
                                            location = message.data.location,
                                            sog = message.data.sog,
                                            heading = message.data.displayHeading
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
                                    val lastMessage = ad.timeUtc
                                    _lastMessageUpdate.update { lastMessage }
                                    messageChannel.trySend(ad)
                                }
                            } catch (e: Exception) {
                                log(Severity.Error, "Parsing-Error", e, withTag = "AIS")
                            }
                        }
                    }
                }
                Result.Success(Unit)
            } catch (_: CancellationException) {
                // DO NOT close the messageChannel inside finally or catch!
                // Just log that the switch happened as intended
                log(Severity.Info, "WebSocket tunnel safely migrated to new coordinates.", withTag = "AIS")
            } catch (e: Exception) {
                _receiverState.update { ReceiverState.connectionLost }
                log(Severity.Error, "Connection error", e, withTag = "AIS")
                throw e
            }
        }
    }

    fun stop() {
        streamJob?.cancel()
        streamJob = null
    }
}
