package de.visualdigits.shipermansfriend.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.errorhandling.LogMessage.Companion.log
import de.visualdigits.common.domain.model.errorhandling.Result
import de.visualdigits.common.domain.model.errorhandling.onError
import de.visualdigits.common.domain.model.errorhandling.onSuccess
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.common.presentation.components.applyAppLanguage
import de.visualdigits.common.presentation.model.CommonAction
import de.visualdigits.common.presentation.model.ScrollIntent
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.error_local_wrong_filetype
import de.visualdigits.generated.AppVersion
import de.visualdigits.shipermansfriend.data.model.aisstreamio.status.ServiceState
import de.visualdigits.shipermansfriend.data.repository.AisStreamClient
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType.Companion.SAFETY_DATA
import de.visualdigits.shipermansfriend.domain.model.errorhandling.DataError
import de.visualdigits.shipermansfriend.domain.model.errorhandling.toUiText
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi.Companion.isValidImo
import de.visualdigits.shipermansfriend.domain.model.geodata.MasterData
import de.visualdigits.shipermansfriend.domain.model.geodata.MmsiPrefix.Companion.fromMmsi
import de.visualdigits.shipermansfriend.domain.model.geodata.PositionData
import de.visualdigits.shipermansfriend.domain.model.geodata.ReceiverState
import de.visualdigits.shipermansfriend.domain.model.geodata.SafetyData
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType
import de.visualdigits.shipermansfriend.domain.model.settings.SK
import de.visualdigits.shipermansfriend.domain.model.settings.Settings
import de.visualdigits.shipermansfriend.domain.model.type.Language
import de.visualdigits.shipermansfriend.domain.model.type.ProgressStage
import de.visualdigits.shipermansfriend.domain.repository.MasterDataRepository
import de.visualdigits.shipermansfriend.domain.repository.SettingsRepository
import de.visualdigits.shipermansfriend.domain.util.formatDistance
import de.visualdigits.shipermansfriend.domain.util.formatSpeed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.io.Sink
import kotlinx.io.Source
import kotlin.math.max
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class ShipermansFriendViewModel(
    private val settingsRepository: SettingsRepository,
    private val masterDataRepository: MasterDataRepository,
    private val aisStreamClient: AisStreamClient,
    scope: CoroutineScope
) : ViewModel() {

    val scrollPosition: MutableMap<String, Triple<Int, Int?, ScrollIntent>> = mutableMapOf()
    var platformType: PlatformType = PlatformType.unknown

    private val _state = MutableStateFlow(ShipermansFriendState())
    val state = _state.asStateFlow()

    private val _editedSettings = MutableStateFlow<Settings?>(null)
    val editedSettings = _editedSettings.asStateFlow()


    private val _positionData = MutableStateFlow<Map<Long, PositionData>>(emptyMap())
    private val _masterData = MutableStateFlow<Map<Long, MasterData>>(emptyMap())
    private val _safetyData = MutableStateFlow<Map<Long, SafetyData>>(emptyMap())

    val location: StateFlow<Location?> = aisStreamClient.location
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val serviceState = aisStreamClient.serviceState
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ServiceState.Down)

    val receiverState = aisStreamClient.receiverState
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ReceiverState.connectionLost)

    val lastLocationUpdateMinutes = aisStreamClient.lastLocationUpdateMinutes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val innerRadius = aisStreamClient.innerRadius
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    init {
        log(Severity.Info, "Application version ${AppVersion().version} initializing...", withTag = "AIS")
        loadData()
        log(Severity.Info, "Application started", withTag = "AIS")

         // fetch existing masterdata from database
        scope.launch {
            masterDataRepository.getAllMasterData()
                .onSuccess { masterDataList ->
                    _masterData.update { current -> current + masterDataList.associateBy { it.mmsi } }
                    log(Severity.Info, "Cache pre-filled with ${masterDataList.size} ships from database.", withTag = "AIS")
                }
        }

        onAction(ShipermansFriendAction.OnInitializeTabs(
            tabLabels = listOf(
                "driving_vessels" to UiText.DynamicString(""),
                "moored_vessels" to UiText.DynamicString(""),
                "safety" to UiText.DynamicString(""),
                "search" to UiText.DynamicString(""),
                "settings" to UiText.DynamicString(""),
                "info" to UiText.DynamicString(""),
            )
        ))

        startAisClient()

        // message collection loop
        scope.launch {
            aisStreamClient.messages
                .collect { message ->
                    aisStreamClient._receiverState.update { ReceiverState.receivingData }

                    when (message) {
                        // collects master data within the outer bounds
                        is MasterData -> {
                            _masterData.update { current ->
                                val mutableCopy = current.toMutableMap()
                                mutableCopy[message.mmsi] = message
                                mutableCopy
                            }
                            masterDataRepository.upsertMasterData(message)
                                .onError { _, throwable ->
                                    log(Severity.Error, "Could not insert master data for mmsi '${message.mmsi}'", throwable, withTag = "AIS")
                                }
                        }
                        // collects position data within the inner bounds
                        is PositionData -> {
                            if (aisStreamClient._innerBoundingBox.value?.let { bb -> message.location.isInBoundingBox(bb) } == true) {
                                _positionData.update { current ->
                                    val mutableCopy = current.toMutableMap()
                                    mutableCopy[message.mmsi] = message
                                    mutableCopy
                                }
                                if (!_masterData.value.containsKey(message.mmsi)) {
                                    launch {
                                        val masterDataResult = masterDataRepository.getMasterData(message.mmsi)
                                        if (masterDataResult is Result.Success) {
                                            masterDataResult.data?.also { md ->
                                                _masterData.update { current -> current + (message.mmsi to md) }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        // collects safety data within the outer bounds
                        is SafetyData -> {
                            if (aisStreamClient._outerBoundingBox.value?.let { bb -> message.location.isInBoundingBox(bb) } == true && message.valid && message.text?.isNotBlank() == true) {
                                log(Severity.Warn, message.toString(), withTag = "SFTY")
                                val exisitingMessage = _safetyData.value[message.mmsi]
                                if (exisitingMessage?.text == message.text) {
                                    return@collect // ignore identical message
                                }
                                _safetyData.update { current ->
                                    val mutableCopy = current.toMutableMap()
                                    mutableCopy[message.mmsi] = message
                                    mutableCopy
                                }
                                _state.update {
                                    it.copy(
                                        hasUnreadSafetyData = true
                                    )
                                }
                            }
                        }
                    }
                }
        }
    }

    // collects safety data within the outer bounds and combines those with known master data
    val safetyDevices: StateFlow<List<AisDataUi>> =
        combine(
            _positionData ,
            _masterData,
            _safetyData,
            location
        ) { positionDataMap, masterDataMap, safetyDataMap, location ->
            safetyDataMap.mapNotNull { (mmsi, safetyData) ->
                val pd = positionDataMap[mmsi]
                if (pd == null) { // only process safety messages without existing position data - those are processed in the other loop
                    val distance = location?.distanceTo(safetyData.location) ?: 0.0
                    val mmsiCountryPrefix = fromMmsi(safetyData.mmsi)
                    AisDataUi(
                        safetyNote = mmsiCountryPrefix.deviceType.label,
                        messageType = safetyData.messageType,
                        mmsi = safetyData.mmsi,
                        mmsiCountryPrefix = mmsiCountryPrefix,
                        timeUtc = safetyData.timeUtc,
                        location = safetyData.location,
                        isMoored = false,
                        shipType = ShipType.SAFETY_DEVICE,
                        distance = distance,
                        distanceString = distance.formatDistance(),
                        hasSafetyMessage = true,
                        messageId = safetyData.messageId,
                        repeatIndicator = safetyData.repeatIndicator,
                        text = safetyData.text,
                        valid = safetyData.valid,
                    )
                } else {
                    null
                }
            }.sortedWith(compareBy<AisDataUi> { ud -> ud.isMoored }
                    .thenBy { ud -> ud.distance })
        }.flowOn(Dispatchers.Default)
            .stateIn(scope, SharingStarted.WhileSubscribed(5000), emptyList())

    // collects position data within the inner bounds and combines those with known master data
    val uiVessels: StateFlow<List<AisDataUi>> =
        combine(
            _positionData ,
            _masterData,
            _safetyData,
            location
        ) { positionDataMap, masterDataMap, safetyDataMap, location ->
            positionDataMap.values
                .map { positionData ->
                    val md = masterDataMap[positionData.mmsi]
                    val sd = safetyDataMap[positionData.mmsi]
                    val distance = location?.distanceTo(positionData.location) ?: 0.0
                    val shipType = if (positionData.messageType == MessageType.BaseStationReport) {
                        ShipType.BASE_STATION
                    } else if (SAFETY_DATA.contains(positionData.messageType)) {
                        ShipType.SAFETY_DEVICE
                    } else {
                        md?.shipType
                    }
                    AisDataUi(
                        messageType = positionData.messageType,
                        name = positionData.name,
                        mmsi = positionData.mmsi,
                        mmsiCountryPrefix = fromMmsi(positionData.mmsi),
                        timeUtc = positionData.timeUtc,
                        location = positionData.location,
                        isMoored = positionData.sog < 0.5,
                        sog = positionData.sog,
                        speedKmh = positionData.sog.formatSpeed(),
                        heading = positionData.heading,
                        imoNumber = if (isValidImo(md?.imoNumber)) md?.imoNumber else 0,
                        callSign = md?.callSign,
                        destination = md?.destination,
                        totalLength = md?.totalLength,
                        totalWidth = md?.totalWidth,
                        shipType = shipType,
                        maximumStaticDraught = md?.maximumStaticDraught,
                        distance = distance,
                        distanceString = distance.formatDistance(),
                        hasSafetyMessage = sd != null && sd.text?.isNotBlank() == true && sd.valid,
                        messageId = sd?.messageId,
                        repeatIndicator = sd?.repeatIndicator,
                        text = sd?.text,
                        valid = sd?.valid,
                    )
                }.sortedWith(compareBy<AisDataUi> { ud -> ud.isMoored }
                    .thenBy { ud -> ud.distance })
        }.flowOn(Dispatchers.Default)
            .stateIn(scope, SharingStarted.WhileSubscribed(5000), emptyList())

    val isolatedSearchText: StateFlow<String> = state
        .map { it.vesselSearchText ?: "" }
        .distinctUntilChanged() // Lässt nur echte Textänderungen durch
        .stateIn(scope, SharingStarted.Eagerly, "") // Macht daraus einen eigenständigen, ruhigen Datenhalter

    // search flow
    val searchedVessels: StateFlow<List<AisDataUi>> = isolatedSearchText
        // Extract the search text field from your global state flow
        // Wait 150ms after typing stops to prevent frantic UI flickering
        .debounce(150.milliseconds)
        .combine(uiVessels) { query, vessels ->
            if (query.isBlank()) {
                emptyList()
            } else {
                val trimmed = query.trim()

                vessels.filter { vessel ->
                    vessel.name.contains(trimmed, ignoreCase = true) ||
                    vessel.callSign?.contains(trimmed, ignoreCase = true) == true ||
                    vessel.shipType?.category?.name?.contains(trimmed, ignoreCase = true) == true ||
                    vessel.mmsi.toString().contains(trimmed) ||
                    vessel.imoNumber.toString().contains(trimmed)
                }
            }
        }
        .flowOn(Dispatchers.Default)
        .stateIn(scope, SharingStarted.WhileSubscribed(5000), emptyList())

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
                startAisClient()
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
                val hasUnreadSafetyData = if (state.value.tabLabels[action.index].first != "safety") {
                    false
                } else {
                    state.value.hasUnreadSafetyData
                }
                _state.update { state ->
                    state.copy(
                        selectedTabIndex = action.index,
                        isEditingSettings = false,
                        hasUnreadSafetyData = hasUnreadSafetyData,
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
                        selectedVessel = action.selectedVessel,
                        previousRadarRadius = it.currentRadarRadius,
                        currentRadarRadius = max(action.selectedVessel.distance, it.currentRadarRadius) // ensure that we can see the selected vessel
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

            is ShipermansFriendAction.OnVesselSearchExpandStateChanged -> {
                _state.update {
                    it.copy(
                        isVesselSearchActive = action.expanded,
                    )
                }
            }

            //
            //
            //
            is ShipermansFriendAction.OnRadarRadiusChange -> {
                if (action.radius < _state.value.currentRadarRadius) {
                    updateRadarRadius(action.radius)
                }
                _state.update {
                    it.copy(
                        previousRadarRadius = it.currentRadarRadius,
                        currentRadarRadius = action.radius
                    )
                }
            }

            is ShipermansFriendAction.OnCollapsibleStateChange -> {
                _state.update {
                    it.copy(
                        collapsibleState = it.collapsibleState + (action.id to action.isExpanded)
                    )
                }
            }

            is ShipermansFriendAction.OnVesselSearchTextChanged -> {
                _state.update {
                    it.copy(
                        vesselSearchText = action.text
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

    private fun updateRadarRadius(radius: Double) {
        location.value?.also { location ->
            val boundingBox = location.calculateBoundingBox(radius)
            _positionData.update { current ->
                val copy = current
                    .toMutableMap()
                    .filter { (_, positionData) ->
                        positionData.location.isInBoundingBox(boundingBox)
                    }
                copy
            }
        }
    }

    private fun importSettings(fileName: String, source: Source) = viewModelScope.launch {
        log(Severity.Info, "Importing settings", withTag = "AIS")
        if (fileName.endsWith(".json", ignoreCase = true)) {
            val settingsResult = settingsRepository.importSettings(source)
            if (settingsResult is Result.Success) {
                val settings = settingsResult.data
                startAisClient()
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
        if (fileName.endsWith(".json", ignoreCase = true)) {
            masterDataRepository.importMasterData(source)
                .onSuccess { _ ->
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
            val radarRadius = finalSettings.get<String>(SK.radiusInner)?.toDouble()?:1000.0

            _state.update {
                it.copy(
                    settings = finalSettings,
                    currentRadarRadius = radarRadius,
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
        settings: Settings?,
    ) = viewModelScope.launch {
        checkNotNull(settings) { "No settings to save" }
        settingsRepository.setSettings(settings)
            .onSuccess {
                val language = settings.get<Language>(SK.language) ?: Language.EN
                applyAppLanguage(language.localeCode)
                val radarRadius = settings.get<String>(SK.radiusInner)?.toDouble() ?: 1000.0
                updateRadarRadius(radarRadius)
                _editedSettings.value = null
                _state.update {
                    it.copy(
                        settings = settings,
                        currentProgress = 0.0f,
                        previousRadarRadius = it.currentRadarRadius,
                        currentRadarRadius = radarRadius,
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

        startAisClient()
    }

    private fun startAisClient() {
        try {
            aisStreamClient.start()
        } catch (_: Exception) {
            _state.update {
                it.copy(
                    currentProgress = 0.0f,
                    isEditingSettings = false,
                    selectedTabIndex = 0,
                    progressStage = ProgressStage.NONE,
                    uiMessage = DataError.Remote.CONNECTION_ERROR.toUiText(),
                    uiMessageSeverity = Severity.Error
                )
            }

        }
    }
}
