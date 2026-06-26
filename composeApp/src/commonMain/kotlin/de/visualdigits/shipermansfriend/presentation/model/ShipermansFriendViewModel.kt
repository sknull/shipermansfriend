package de.visualdigits.shipermansfriend.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.errorhandling.LogMessage.Companion.log
import de.visualdigits.common.domain.model.errorhandling.Result
import de.visualdigits.common.domain.model.errorhandling.onError
import de.visualdigits.common.domain.model.errorhandling.onSuccess
import de.visualdigits.common.domain.model.geodata.formatDistance
import de.visualdigits.common.domain.model.platform.ConnectivityMode
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.common.presentation.components.ConnectivityManager
import de.visualdigits.common.presentation.components.applyAppLanguage
import de.visualdigits.common.presentation.model.CommonAction
import de.visualdigits.common.presentation.model.ScrollIntent
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.error_local_wrong_filetype
import de.visualdigits.compose.resources.tab_categories
import de.visualdigits.compose.resources.tab_driving_vessels
import de.visualdigits.compose.resources.tab_moored_vessels
import de.visualdigits.compose.resources.tab_settings
import de.visualdigits.generated.AppVersion
import de.visualdigits.shipermansfriend.data.model.aisstreamio.status.ServiceState
import de.visualdigits.shipermansfriend.data.repository.AisStreamClient
import de.visualdigits.shipermansfriend.domain.model.errorhandling.toUiText
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.MasterData
import de.visualdigits.shipermansfriend.domain.model.geodata.PositionData
import de.visualdigits.shipermansfriend.domain.model.geodata.ReceiverState
import de.visualdigits.shipermansfriend.domain.model.settings.SK
import de.visualdigits.shipermansfriend.domain.model.settings.Settings
import de.visualdigits.shipermansfriend.domain.model.type.Language
import de.visualdigits.shipermansfriend.domain.model.type.ProgressStage
import de.visualdigits.shipermansfriend.domain.repository.MasterDataRepository
import de.visualdigits.shipermansfriend.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.io.Sink
import kotlinx.io.Source
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class ShipermansFriendViewModel(
    private val settingsRepository: SettingsRepository,
    private val masterDataRepository: MasterDataRepository,
    private val aisStreamClient: AisStreamClient,
    private val connectivityManager: ConnectivityManager,
    scope: CoroutineScope
) : ViewModel() {

    val scrollPosition: MutableMap<String, Triple<Int, Int?, ScrollIntent>> = mutableMapOf()
    var platformType: PlatformType = PlatformType.unknown

    private val _state = MutableStateFlow(ShipermansFriendState())
    val state = _state.asStateFlow()

    private val _editedSettings = MutableStateFlow<Settings?>(null)
    val editedSettings = _editedSettings.asStateFlow()
    private val retryCount = MutableStateFlow(0)

    private val _serviceState = MutableStateFlow<ServiceState?>(null)
    val serviceState = _serviceState.asStateFlow()

    private val _positionData = MutableStateFlow<Map<Long, PositionData>>(emptyMap())
    val positionData = _positionData.asStateFlow()

    private val masterData = MutableStateFlow<Map<Long, MasterData>>(emptyMap())

    companion object {

        private val MAX_INACTIVITY_MINUTES: Duration = 5.minutes
    }
    // landungsbrücken:

    init {
        log(Severity.Info, "Application version ${AppVersion().version} initializing...", withTag = "AIS")
        loadData()
        log(Severity.Info, "Application started", withTag = "AIS")
        log(Severity.Debug, "Settings: ${state.value.settings}", withTag = "AIS")

        // fetch existing masterdata from database
        scope.launch {
            masterDataRepository.getAllMasterData()
                .onSuccess { masterDataList ->
                    masterData.update { current -> current + masterDataList.associateBy { it.mmsi } }
                    log(Severity.Info, "Cache pre-filled with ${masterDataList.size} ships from database.", withTag = "AIS")
                }
        }

        onAction(ShipermansFriendAction.OnInitializeTabs(
            tabLabels = listOf(
                "driving_vessels" to UiText.StringResourceId(Res.string.tab_driving_vessels),
                "moored_vessels" to UiText.StringResourceId(Res.string.tab_moored_vessels),
                "categories" to UiText.StringResourceId(Res.string.tab_categories),
                "settings" to UiText.StringResourceId(Res.string.tab_settings)
            )
        ))

        aisStreamClient.start()

        // message collection loop
        scope.launch {
            aisStreamClient.messages
                .collect { message ->
                    aisStreamClient._lastLocationUpdateMinutes.update { KmpOffsetDateTime.now().minus(aisStreamClient.lastLocationUpdate.value).inWholeMinutes  }
                    aisStreamClient._receiverState.update { ReceiverState.receivingData }

                    when (message) {
                        // collects master data within the outer bounds the client was configured with
                        is MasterData -> {
                            masterData.update { current -> current + (message.mmsi to message) }
                            masterDataRepository.upsertMasterData(message)
                                .onError { local, throwable ->
                                    log(Severity.Error, "Could not insert master data for mmsi '${message.mmsi}'", throwable, withTag = "AIS")
                                }
                        }
                        // collects position data within the inner bounds the client was configured with
                        is PositionData -> {
                            if (aisStreamClient.innerBoundingBox.value?.let { bb -> message.location.isInBoundingBox(bb) } == true) {
                                _positionData.update { current -> current + (message.mmsi to message) }
                                if (!masterData.value.containsKey(message.mmsi)) {
                                    launch {
                                        val masterDataResult = masterDataRepository.getMasterData(message.mmsi)
                                        if (masterDataResult is Result.Success) {
                                            masterDataResult.data?.also { md ->
                                                masterData.update { current -> current + (message.mmsi to md) }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        }

        // iterate through states with progressive delays
        scope.launch {
            aisStreamClient._receiverState
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
                                aisStreamClient.start()
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
                    aisStreamClient._receiverState.value = resetValue
                }
        }

        // monitor location change
        scope.launch {
            while (isActive) {
                if (aisStreamClient.location.value != aisStreamClient._previousLocation.value) {
                    log(Severity.Info, "Location changed - clearing position data")
                    aisStreamClient._previousLocation.update { aisStreamClient.location.value }
                    _positionData.update { emptyMap() }
                }
                delay(500.milliseconds)
            }
        }

        // monitor service state
        scope.launch {
            while (isActive) {
                val serviceStatus = withContext(Dispatchers.IO) {
                    aisStreamClient.serviceStatus()
                }
                _serviceState.update { serviceStatus?.state }
                delay(10.seconds)
            }
        }

        // monitor incoming messages
        scope.launch {
            while (isActive) {
                val lastMsgTime = aisStreamClient.lastMessage.value
                val minutesSinceLastMessage = KmpOffsetDateTime.now().minus(lastMsgTime)

                if (minutesSinceLastMessage > MAX_INACTIVITY_MINUTES && aisStreamClient.receiverState.value == ReceiverState.noData) {
                    log(Severity.Info, "No messages for $minutesSinceLastMessage minutes. Checking Health Endpoint...", withTag = "AIS")

                    if (_serviceState.value == ServiceState.Down) {
                        aisStreamClient._receiverState.update { ReceiverState.serverDown }
                    } else {
                        aisStreamClient._receiverState.update { ReceiverState.connectionLost }
                    }

                    delay(4.minutes + 30.seconds) // extra delay to make sure we check status not too often
                }

                delay(30.seconds)
            }
        }
    }

    // collects position data within the inner bounds
    val uiVessels: StateFlow<List<AisDataUi>> =
        combine(_positionData, masterData, aisStreamClient.location) { positions, masterDataMap, location ->
            positions.values
                .map { positionData ->
                    val md = masterDataMap[positionData.mmsi]
                    val distance = location?.let { l -> positionData.location.distanceTo(l) } ?: 0.0
                    AisDataUi(
                        name = positionData.name,
                        mmsi = positionData.mmsi,
                        timeUtc = positionData.timeUtc,
                        location = positionData.location,
                        isMoored = positionData.sog < 0.5,
                        sog = positionData.sog,
                        heading = positionData.heading,
                        imoNumber = md?.imoNumber,
                        callSign = md?.callSign,
                        destination = md?.destination,
                        totalLength = md?.totalLength,
                        totalWidth = md?.totalWidth,
                        shipType = md?.shipType,
                        maximumStaticDraught = md?.maximumStaticDraught,
                        distance = distance,
                        distanceString = distance.formatDistance()
                    )
                }.sortedWith(compareBy<AisDataUi> { ud -> ud.isMoored }
                    .thenBy { ud -> ud.distance })
        }.stateIn(scope, SharingStarted.WhileSubscribed(5000), emptyList())


    fun clearPositionData() {
        _positionData.update { emptyMap() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun onCommonAction(action: CommonAction) {
        when (action) {
            is CommonAction.OnScrollPositionChange -> {
                action.id?.also { id ->
                    scrollPosition[id] = Triple(action.position, action.offset, action.scrollIntent)
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun onAction(action: ShipermansFriendAction) {
        when (action) {

            //
            // Settings
            //
            is ShipermansFriendAction.OnEditSettingsClick -> {
                _editedSettings.value = state.value.settings
                _state.update {
                    it.copy(
                        isEditingSettings = action.isEditingSettings,
                        isShowInfos = false,
                        uiMessage = null,
                        uiMessageSeverity = null
                    )
                }
            }

            is ShipermansFriendAction.OnSettingsValueChanged -> {
                _editedSettings.update { current ->
                    current?.copy(
                        key = action.keyValue.descriptor.key as SK,
                        value = action.keyValue.value
                    )
                }
            }

            is ShipermansFriendAction.OnEditSettingsCancelClick -> {
                _state.update { state ->
                    state.settings?.get<Language>(SK.language)?.also { l -> applyAppLanguage(l.localeCode) }
                    state.copy(
                        isEditingSettings = false,
                        selectedTabIndex = 0,
                        uiMessage = null,
                        uiMessageSeverity = null
                    )
                }
            }

            is ShipermansFriendAction.OnSaveSettingsClick -> {
                saveSettings(_editedSettings.value)
            }

            is ShipermansFriendAction.OnSettingsImport -> {
                importSettings(action.fileName, action.source)
            }

            is ShipermansFriendAction.OnSettingsExport -> {
                exportSettings(action.fileName, action.sink)
            }

            is ShipermansFriendAction.OnMasterDataImport -> {
                importMasterData(action.fileName, action.source)
            }

            is ShipermansFriendAction.OnMasterDataExport -> {
                exportMasterData(action.fileName, action.sink)
            }

            is ShipermansFriendAction.OnReconnect -> {
                aisStreamClient.start()
            }

            is ShipermansFriendAction.UpdateMaxImageSize -> {
                action.settings?.also { settings ->
                    saveSettings(settings.copy(SK.maxImageSize, action.maxImageSize))
                }
                _state.update {
                    it.copy(
                        maxImageSize = action.maxImageSize
                    )
                }
            }

            //
            // Tabs
            //
            is ShipermansFriendAction.OnInitializeTabs -> {
                _state.update {
                    it.copy(
                        tabLabels = action.tabLabels,
                        selectedTabIndex = 0,
                        isEditingSettings = false,
                        isShowInfos = false,
                        uiMessage = null,
                        uiMessageSeverity = null
                    )
                }
            }

            is ShipermansFriendAction.OnTabSelected -> {
                if (state.value.tabLabels[action.index].first == "settings") {
                    _editedSettings.value = state.value.settings
                }
                _state.update { state ->
                    state.copy(
                        selectedTabIndex = action.index,
                        isEditingSettings = false,
                        isShowInfos = false,
                        uiMessage = null,
                        uiMessageSeverity = null,
                        vessels = listOf(),
                        selectedVessel = null
                    )
                }
            }

            //
            // Vessels
            //
            is ShipermansFriendAction.OnShowRadar -> {
                _state.update {
                    it.copy(
                        vessels = action.vessels,
                        selectedVessel = action.selectedVessel
                    )
                }
            }
            is ShipermansFriendAction.OnShowRadarBack -> {
                _state.update {
                    it.copy(
                        vessels = listOf(),
                        selectedVessel = null
                    )
                }
            }

            //
            //
            //
            is ShipermansFriendAction.OnCollapsibleStateChange -> {
                _state.update {
                    it.copy(
                        collapsibleState = it.collapsibleState + (action.id to action.isExpanded)
                    )
                }
            }

            is ShipermansFriendAction.OnLanguageSelected -> {
                applyAppLanguage(action.language.localeCode)
                _state.update {
                    it.copy(
                        language = action.language
                    )
                }
            }

            is ShipermansFriendAction.OnShowInfosClick -> {
                _state.update {
                    it.copy(
                        isShowInfos = action.isShowInfos,
                        isEditingSettings = false,
                        uiMessage = null,
                        uiMessageSeverity = null
                    )
                }
            }
        }
    }

    private fun importSettings(fileName: String, source: Source) = viewModelScope.launch {
        log(Severity.Info, "Importing settings", withTag = "AIS")
        if (fileName.endsWith(".json", ignoreCase = true)) {
            val settingsResult = settingsRepository.importSettings(source)
            if (settingsResult is Result.Success) {
                val settings = settingsResult.data
                aisStreamClient.start()
                _state.update {
                    it.copy(
                        settings = settings,
                        isEditingSettings = false,
                        selectedTabIndex = 0,
                        uiMessage = null,
                    )
                }
            } else if (settingsResult is Result.Error) {
                log(Severity.Error, "Could not import settings", settingsResult.throwable, withTag = "AIS")
                _state.update {
                    it.copy(
                        uiMessage = settingsResult.error.toUiText(),
                        uiMessageSeverity = Severity.Error,
                        isEditingSettings = false,
                        selectedTabIndex = 0,
                    )
                }
            }
        } else {
            _state.update {
                it.copy(
                    currentProgress = 0.0f,
                    progressStage = ProgressStage.NONE,
                    uiMessage = UiText.StringResourceId(Res.string.error_local_wrong_filetype),
                    uiMessageSeverity = Severity.Error
                )
            }
        }
    }

    private fun exportSettings(fileName: String, sink: Sink) = viewModelScope.launch {
        log(Severity.Info, "Exporting settings", withTag = "AIS")
        if (fileName.endsWith(".json", ignoreCase = true)) {
            val settings = state.value.settings
            if(settings != null) {
                settingsRepository.exportSettings(settings, sink)
                    .onSuccess {
                        _state.update {
                            it.copy(
                                uiMessage = null,
                            )
                        }
                    }
                    .onError { error, throwable ->
                        log(Severity.Error, "Could not export settings", throwable, withTag = "AIS")
                        _state.update {
                            it.copy(
                                uiMessage = error.toUiText(),
                                uiMessageSeverity = Severity.Error
                            )
                        }
                    }
            }
        } else {
            _state.update {
                it.copy(
                    currentProgress = 0.0f,
                    progressStage = ProgressStage.NONE,
                    uiMessage = UiText.StringResourceId(Res.string.error_local_wrong_filetype),
                    uiMessageSeverity = Severity.Error
                )
            }
        }
    }

    private fun importMasterData(fileName: String, source: Source) = viewModelScope.launch {
        log(Severity.Info, "Importing masterdata", withTag = "AIS")
        if (fileName.endsWith(".json", ignoreCase = true)) {
            masterDataRepository.importMasterData(source)
                .onSuccess { settings ->
                    _state.update {
                        it.copy(
                            uiMessage = null,
                        )
                    }
                }
                .onError { error, throwable ->
                    log(Severity.Error, "Could not import masterdata", throwable, withTag = "AIS")
                    _state.update {
                        it.copy(
                            uiMessage = error.toUiText(),
                            uiMessageSeverity = Severity.Error
                        )
                    }
                }
        } else {
            _state.update {
                it.copy(
                    currentProgress = 0.0f,
                    progressStage = ProgressStage.NONE,
                    uiMessage = UiText.StringResourceId(Res.string.error_local_wrong_filetype),
                    uiMessageSeverity = Severity.Error
                )
            }
        }
    }

    private fun exportMasterData(fileName: String, sink: Sink) = viewModelScope.launch {
        log(Severity.Info, "Exporting masterdata", withTag = "AIS")
        if (fileName.endsWith(".json", ignoreCase = true)) {
            masterDataRepository.exportMasterData(sink)
                .onSuccess {
                    _state.update {
                        it.copy(
                            uiMessage = null,
                        )
                    }
                }
                .onError { error, throwable ->
                    log(Severity.Error, "Could not export masterdata", throwable, withTag = "AIS")
                    _state.update {
                        it.copy(
                            uiMessage = error.toUiText(),
                            uiMessageSeverity = Severity.Error
                        )
                    }
                }
        } else {
            _state.update {
                it.copy(
                    currentProgress = 0.0f,
                    progressStage = ProgressStage.NONE,
                    uiMessage = UiText.StringResourceId(Res.string.error_local_wrong_filetype),
                    uiMessageSeverity = Severity.Error
                )
            }
        }
    }

    private fun loadData() = viewModelScope.launch {
        _state.update {
            it.copy(
                currentProgress = 0.0f,
                progressStage = ProgressStage.NONE,
            )
        }
        val result = settingsRepository.getSettings()
        if (result is Result.Success) {
            val settings = result.data
            val finalSettings = if (settings != null) {
                settings
            } else {
                val newSettings = Settings(mapOf(
                    SK.language to Language.EN,
                    SK.maxImageSize to 1200,
                ))
                settingsRepository.setSettings(newSettings)
                    .onError { _, throwable ->
                        log(Severity.Error, "Could not safe initial settings", throwable, withTag = "AIS")
                    }
                newSettings
            }

            applyAppLanguage(finalSettings.get<Language>(SK.language)?.localeCode?: Language.EN.localeCode)

            _state.update {
                it.copy(
                    settings = finalSettings,
                    currentProgress = 0.0f,
                    progressStage = ProgressStage.NONE,
                    uiMessage = null,
                    uiMessageSeverity = null,
                    collapsibleState = mapOf("group_newsfeeds_navigation" to true)
                )
            }
        } else if (result is Result.Error) {
            log(Severity.Error, "Could not load data", result.throwable, withTag = "AIS")
            _state.update {
                it.copy(
                    currentProgress = 0.0f,
                    progressStage = ProgressStage.NONE,
                    uiMessage = result.error.toUiText(),
                    uiMessageSeverity = Severity.Error
                )
            }
        }
    }

    private fun saveSettings(
        editedSettings: Settings?,
    ) = viewModelScope.launch {
        checkNotNull(editedSettings) { "No settings to save" }
        settingsRepository.setSettings(editedSettings)
            .onSuccess {
                val language = editedSettings.get<Language>(SK.language) ?: Language.EN
                applyAppLanguage(language.localeCode)
                _editedSettings.value = null
                _state.update {
                    it.copy(
                        settings = editedSettings,
                        currentProgress = 0.0f,
                        progressStage = ProgressStage.NONE,
                        isEditingSettings = false,
                        selectedTabIndex = 0,
                        uiMessage = null,
                        uiMessageSeverity = null
                    )
                }
            }
            .onError { error, throwable ->
                log(Severity.Error, "Could not save settings", throwable, withTag = "AIS")
                _state.update {
                    it.copy(
                        currentProgress = 0.0f,
                        isEditingSettings = false,
                        selectedTabIndex = 0,
                        progressStage = ProgressStage.NONE,
                        uiMessage = error.toUiText(),
                        uiMessageSeverity = Severity.Error
                    )
                }
            }

        aisStreamClient.start()
    }
}
