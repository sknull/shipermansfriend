package de.visualdigits.shipermansfriend.data.repository

import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.configuration.keyfactory.BooleanEnum
import de.visualdigits.common.domain.model.errorhandling.LogMessage.Companion.log
import de.visualdigits.common.domain.model.errorhandling.Result
import de.visualdigits.common.domain.model.geodata.BoundingBox
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.model.geodata.toLocation
import de.visualdigits.shipermansfriend.data.model.aisstreamio.AisMessageWrapper
import de.visualdigits.shipermansfriend.data.model.aisstreamio.PositionAisMessageWrapper
import de.visualdigits.shipermansfriend.data.model.aisstreamio.StaticDataAisMessageWrapper
import de.visualdigits.shipermansfriend.data.model.aisstreamio.status.ServiceStatus
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.ApiKey
import de.visualdigits.shipermansfriend.domain.model.geodata.AisData
import de.visualdigits.shipermansfriend.domain.model.geodata.MasterData
import de.visualdigits.shipermansfriend.domain.model.geodata.PositionData
import de.visualdigits.shipermansfriend.domain.model.geodata.ReceiverState
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

class AisStreamClient(
    private val httpClient: HttpClient,
    private val settingsRepository: SettingsRepository,
    private val locationProvider: LocationProvider,
    private val scope: CoroutineScope,
) {

    companion object {

        private val aisJson = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            encodeDefaults = true
        }

        private const val HOST_URI = "wss://stream.aisstream.io/v0/stream"
        private const val THRESHOLD_METERS = 500.0
    }

    private var streamJob: Job? = null
    private var locationJob: Job? = null
    private var initializerJob: Job? = null
    private var activeApiKey: ApiKey? = null
    private var lastStreamingLocation: Location? = null

    private val messageChannel = Channel<AisData>(Channel.BUFFERED)

    private val _location = MutableStateFlow<Location?>(null)
    val location = _location.asStateFlow()

    val _previousLocation = MutableStateFlow<Location?>(null)

    private val _lastMessage = MutableStateFlow<KmpOffsetDateTime>(KmpOffsetDateTime.now())
    val lastMessage = _lastMessage.asStateFlow()

    private val _lastLocationUpdate = MutableStateFlow<KmpOffsetDateTime>(KmpOffsetDateTime.now())
    val lastLocationUpdate = _lastLocationUpdate.asStateFlow()

    val _lastLocationUpdateMinutes = MutableStateFlow<Long>(0)
    val lastLocationUpdateMinutes = _lastLocationUpdateMinutes.asStateFlow()

    val _receiverState = MutableStateFlow(ReceiverState.noData)
    val receiverState: StateFlow<ReceiverState> = _receiverState.asStateFlow()

    val innerBoundingBox = MutableStateFlow<BoundingBox?>(null)

    val messages: Flow<AisData> = messageChannel
        .receiveAsFlow()
        .shareIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            replay = 0
        )

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
                    val innerRadius = settings.get<String>(SK.radiusInner)?.toDoubleOrNull()

                    if (savedKey?.isNotBlank() == true && outerRadius != null && innerRadius != null) {
                        val dbLocation = settings.get<String>(SK.location)?.toLocation()

                        if (useGpsLocation) {
                            log(Severity.Warn, "GPS active. Starting location observation...", withTag = "AIS")

                            val fallbackJob = launch {
                                delay(4.seconds)
                                if (lastStreamingLocation == null && dbLocation != null) {
                                    log(Severity.Warn, "GPS delayed. Using initial database location fallback.", withTag = "AIS")
                                    processNewLocation(dbLocation, savedKey, outerRadius, innerRadius, useGpsLocation = false)
                                }
                            }

                            // Launch the infinite loop completely decoupled on the parent scope
                            locationJob = scope.launch(SupervisorJob() + Dispatchers.Default) {
                                locationProvider.observeLocation().collect { currentGpsLocation ->
                                    fallbackJob.cancel()
                                    processNewLocation(currentGpsLocation, savedKey, outerRadius, innerRadius, useGpsLocation = true)
                                }
                            }
                        } else {
                            if (dbLocation != null) {
                                processNewLocation(dbLocation, savedKey, outerRadius, innerRadius, useGpsLocation = false)
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
        innerBoundingBox.value = targetLocation.calculateBoundingBox(innerRadius)
        val outerBoundingBox = targetLocation.calculateBoundingBox(outerRadius)
        val apiKey = ApiKey(apiKey = savedKey, boundingBoxes = outerBoundingBox.toList())
        _previousLocation.value = _location.value
        _location.value = targetLocation
        _lastLocationUpdate.value = KmpOffsetDateTime.now()

        log(Severity.Info, "location updated: ${targetLocation.toDmsString()}", withTag = "AIS")
        log(Severity.Info, "outerRadius: $outerRadius", withTag = "AIS")
        log(Severity.Info, "innerRadius: $innerRadius", withTag = "AIS")
        log(Severity.Info, "outerBoundingBox: $outerBoundingBox", withTag = "AIS")
        log(Severity.Info, "innerBoundingBox: ${innerBoundingBox.value}", withTag = "AIS")
        log(Severity.Info, "Starting ais client for new bounding box", withTag = "AIS")

        start(apiKey)
    }

    fun start(apiKey: ApiKey) {
        if (activeApiKey == apiKey && streamJob?.isActive == true) {
            log(Severity.Info, "Client is running already with same parameters.", withTag = "AIS")
            return
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
                    val authJson = aisJson.encodeToString(apiKey)
                    send(Frame.Text(authJson))

                    for (frame in incoming) {
                        if (frame is Frame.Binary) {
                            try {
                                val jsonString = frame.readBytes().decodeToString()
                                val message = aisJson.decodeFromString<AisMessageWrapper>(jsonString)
                                val aisData = when (message) {
                                    is StaticDataAisMessageWrapper -> {
                                        MasterData(
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
                                    is PositionAisMessageWrapper -> {
                                        PositionData(
                                            name = message.metaData.shipName.trim(),
                                            mmsi = message.metaData.mmsi,
                                            timeUtc = KmpOffsetDateTime.fromString(message.metaData.timeUtc),
                                            location = message.data.location,
                                            sog = message.data.sog,
                                            heading = message.data.displayHeading
                                        )
                                    }
                                    else -> null
                                }
                                // Use trySend to avoid blocking inside the synchronized loop
                                aisData?.also { ad ->
                                    val lastMessage = ad.timeUtc
                                    _lastMessage.update { lastMessage }
                                    messageChannel.trySend(ad)
                                }
                            } catch (e: Exception) {
                                log(Severity.Error, "Parsing-Error", e, withTag = "AIS")
                            }
                        }
                    }
                }
            } catch (_: CancellationException) {
                // DO NOT close the messageChannel inside finally or catch!
                // Just log that the switch happened as intended
                log(Severity.Info, "WebSocket tunnel safely migrated to new coordinates.", withTag = "AIS")
            } catch (e: Exception) {
                _receiverState.update { ReceiverState.connectionLost }
                log(Severity.Error, "Connection error", e, withTag = "AIS")
            }
        }
    }

    fun stop() {
        streamJob?.cancel()
        streamJob = null
    }
}
