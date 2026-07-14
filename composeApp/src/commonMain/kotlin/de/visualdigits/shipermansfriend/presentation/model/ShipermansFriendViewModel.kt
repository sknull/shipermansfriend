package de.visualdigits.shipermansfriend.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.errorhandling.Result
import de.visualdigits.common.domain.model.errorhandling.onError
import de.visualdigits.common.domain.model.errorhandling.onSuccess
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.model.platform.ConnectivityMode
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.common.presentation.components.applyAppLanguage
import de.visualdigits.common.presentation.model.CommonAction
import de.visualdigits.common.presentation.model.ScrollIntent
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.error_local_wrong_filetype
import de.visualdigits.generated.AppVersion
import de.visualdigits.shipermansfriend.data.repository.AisStreamClient
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.AisStreamState
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType.Companion.SAFETY_DATA
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.ReceivingDataState
import de.visualdigits.shipermansfriend.domain.model.errorhandling.DataError
import de.visualdigits.shipermansfriend.domain.model.errorhandling.toUiText
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi.Companion.isValidImo
import de.visualdigits.shipermansfriend.domain.model.geodata.MasterData
import de.visualdigits.shipermansfriend.domain.model.geodata.MovementDirection
import de.visualdigits.shipermansfriend.domain.model.geodata.PositionData
import de.visualdigits.shipermansfriend.domain.model.geodata.SafetyData
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType
import de.visualdigits.shipermansfriend.domain.model.geodata.mmsi.MmsiPrefix.Companion.fromMmsi
import de.visualdigits.shipermansfriend.domain.model.settings.SK
import de.visualdigits.shipermansfriend.domain.model.settings.Settings
import de.visualdigits.shipermansfriend.domain.model.type.CategoryMode
import de.visualdigits.shipermansfriend.domain.model.type.Language
import de.visualdigits.shipermansfriend.domain.model.type.ProgressStage
import de.visualdigits.shipermansfriend.domain.repository.MasterDataRepository
import de.visualdigits.shipermansfriend.domain.repository.SettingsRepository
import de.visualdigits.shipermansfriend.domain.repository.StarredVesselRepository
import de.visualdigits.shipermansfriend.domain.util.notBlank
import de.visualdigits.shipermansfriend.domain.util.parseDistance
import de.visualdigits.shipermansfriend.domain.util.toKmh
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
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class ShipermansFriendViewModel(
    private val settingsRepository: SettingsRepository,
    private val masterDataRepository: MasterDataRepository,
    private val starredVesselRepository: StarredVesselRepository,
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

    private val _vesselsStarred = MutableStateFlow<Map<Long, AisDataUi>>(emptyMap())
    val vesselsStarred = _vesselsStarred.asStateFlow()

    val location: StateFlow<Location?> = aisStreamClient._location.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val innerRadius: StateFlow<Double> = aisStreamClient._innerRadius.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 1000.0)

    val connectivityMode = aisStreamClient._connectivityMode.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ConnectivityMode.disconnected)

    val aisStreamState = aisStreamClient._aisStreamState.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AisStreamState.Down)

    val receivingDataState = aisStreamClient._receivingDataState.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ReceivingDataState.disconnected)

    val lastLocationUpdateDuration = aisStreamClient._lastLocationUpdateDuration.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.seconds)

    init {
        Logger.i("Application version ${AppVersion().version} initializing...")
        loadData()
        Logger.i("Application started")

         // fetch existing masterdata from database
        scope.launch {
            masterDataRepository.getAllMasterData()
                .onSuccess { masterDataList ->
                    _masterData.update { current -> current + masterDataList.associateBy { it.mmsi } }
                    Logger.i("Cache pre-filled with ${masterDataList.size} ships from database.")
                }
            starredVesselRepository.getAllStarredVessels()
                .onSuccess { starredVessels ->
                    _vesselsStarred.update { starredVessels.associateBy { ppe -> ppe.mmsi } }
                    Logger.i("Restored ${starredVessels.size} starred vessels entries from database.")
                }
        }

        onAction(ShipermansFriendAction.OnInitializeTabs(
            tabLabels = listOf(
                "driving_vessels" to UiText.DynamicString(""),
                "moored_vessels" to UiText.DynamicString(""),
                "starred_vessels" to UiText.DynamicString(""),
                "alerted_vessels" to UiText.DynamicString(""),
                "safety" to UiText.DynamicString(""),
                "search" to UiText.DynamicString(""),
                "settings" to UiText.DynamicString(""),
                "info" to UiText.DynamicString(""),
                "radar" to UiText.DynamicString(""),
            )
        ))

        startAisClient()

        // collect master data
        scope.launch(Dispatchers.Default) {
            aisStreamClient.masterData
                .collect { message ->
                    aisStreamClient._receivingDataState.update { ReceivingDataState.receivingData }
                    if (_state.value.isReconnecting) {
                        _state.update { it.copy(isReconnecting = false) }
                    }
                    _masterData.update { current -> current + (message.mmsi to message) }
                }
        }

        // collect position data
        scope.launch(Dispatchers.Default) {
            aisStreamClient.positionData
                .collect { message ->
                    aisStreamClient._receivingDataState.update { ReceivingDataState.receivingData }
                    if (_state.value.isReconnecting) {
                        _state.update { it.copy(isReconnecting = false) }
                    }
                    _positionData.update { current -> current + (message.mmsi to message) }

                    // update data for starred vessel (if any)
                    if (_vesselsStarred.value.contains(message.mmsi)) {
                        val vessel = _vesselsStarred.value[message.mmsi]!!.copy(
                            messageType = message.messageType,
                            timeUtc = message.timeUtc,
                            location = message.location,
                            sog = message.sog,
                            heading = message.heading,
                            rateOfTurnDegreesPerMinute = message.rateOfTurnDegreesPerMinute,
                            navigationalStatus = message.navigationalStatus
                        )
                        _vesselsStarred.update { current -> current + (message.mmsi to vessel) }
                        starredVesselRepository.upsertStarredVessel(vessel)
                    }

                    // try to look up master data from database when we have it not cached already
                    if (!_masterData.value.containsKey(message.mmsi)) {
                        val masterDataResult = masterDataRepository.getMasterData(message.mmsi)
                        if (masterDataResult is Result.Success) {
                            masterDataResult.data?.also { md ->
                                _masterData.update { current -> current + (message.mmsi to md) }
                            }
                        }
                    }
                }
        }

        // collect safety messages
        scope.launch(Dispatchers.Default) {
            aisStreamClient.safetyMessages
                .collect { message ->
                    aisStreamClient._receivingDataState.update { ReceivingDataState.receivingData }
                    if (_state.value.isReconnecting || !_state.value.hasUnreadSafetyData) {
                        _state.update {
                            it.copy(
                                isReconnecting = false,
                                hasUnreadSafetyData = true
                            )
                        }
                    }
                    _safetyData.update { current -> current + (message.mmsi to message) }
                }
        }
    }

    // combines position data, master data and safety messages into one ui object
    private val uiVessels: StateFlow<List<AisDataUi>> =
        combine(
            _positionData ,
            _masterData,
            _safetyData,
            location
        ) { positionDataMap,
            masterDataMap,
            safetyDataMap,
            location ->
            val currentTime = KmpOffsetDateTime.now()
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
                        md?.shipType ?: ShipType.Unknown_0
                    }
                    AisDataUi(
                        messageType = positionData.messageType,
                        name = positionData.name,
                        mmsi = positionData.mmsi,
                        mmsiCountryPrefix = fromMmsi(positionData.mmsi),
                        timeUtc = positionData.timeUtc,
                        location = positionData.location,
                        observingLocation = location,
                        timeUtcObserved = currentTime,
                        sog = positionData.sog,
                        speedKmh = positionData.sog.toKmh(),
                        heading = positionData.heading,
                        rateOfTurnDegreesPerMinute = positionData.rateOfTurnDegreesPerMinute,
                        navigationalStatus = positionData.navigationalStatus,
                        imoNumber = if (isValidImo(md?.imoNumber)) md?.imoNumber else null,
                        callSign = md?.callSign,
                        destination = md?.destination,
                        totalLength = md?.totalLength,
                        totalWidth = md?.totalWidth,
                        shipType = shipType,
                        maximumStaticDraught = md?.maximumStaticDraught,
                        distance = distance,
                        hasSafetyMessage = sd != null && sd.text?.isNotBlank() == true && sd.valid,
                        messageId = sd?.messageId,
                        repeatIndicator = sd?.repeatIndicator,
                        text = sd?.text,
                        valid = sd?.valid,
                    )
                }
        }.flowOn(Dispatchers.Default)
            .stateIn(scope, SharingStarted.Eagerly, emptyList())

    private val vesselsAlerted: StateFlow<List<AisDataUi>> =
        combine(
            uiVessels,
            innerRadius,
            location,
            state.map { it.alertVessels }.distinctUntilChanged()
        ) { uiVessels,
            innerRadius,
            location,
            alertVessels ->
            uiVessels.mapNotNull { vessel ->
                if (alertVessels.contains(vessel.mmsi)) {
                    if (location?.let { l -> vessel.distance  < innerRadius }  == true) {
                        vessel
                    } else {
                        null
                    }
                } else {
                    null
                }
            }.sortedBy { vessel -> vessel.distance }
        }.distinctUntilChanged()
            .flowOn(Dispatchers.Default)
            .stateIn(scope, SharingStarted.Eagerly, emptyList())

    val vesselsAlertedGrouped: StateFlow<Map<MovementDirection, List<AisDataUi>>> =
        combine(vesselsAlerted, location) {
                searchedVessels, location ->
            searchedVessels.groupBy { vessel -> location?.let { l -> vessel.movementDirection(l) } ?: MovementDirection.UNKNOWN }
        }.distinctUntilChanged()
            .flowOn(Dispatchers.Default)
            .stateIn(scope, SharingStarted.WhileSubscribed(5000), emptyMap())

    val vesselsAlertedMssis: StateFlow<List<Long>> = vesselsAlerted.map { vessels ->
        vessels.map { vessel -> vessel.mmsi }
    }.distinctUntilChanged()
        .flowOn(Dispatchers.Default)
        .stateIn(scope, SharingStarted.WhileSubscribed(5000), emptyList())

    val vesselsStarredGrouped: StateFlow<Map<MovementDirection, List<AisDataUi>>> =
        combine(
            _vesselsStarred,
            location
        ) {
                starredVessels, location ->
            starredVessels.values
                .sortedBy { vessel -> vessel.distance }
                .groupBy { vessel ->
                    location?.let { l -> vessel.movementDirection(l) } ?: MovementDirection.UNKNOWN
                }
        }.distinctUntilChanged()
            .flowOn(Dispatchers.Default)
            .stateIn(scope, SharingStarted.WhileSubscribed(5000), emptyMap())

    private val vesselsInInnerRadius: StateFlow<List<AisDataUi>> =
        combine(
            uiVessels,
            innerRadius
        ) { uiVessels, innerRadius ->
            uiVessels
                .filter { vessel -> vessel.distance < innerRadius }
        }.distinctUntilChanged()
            .flowOn(Dispatchers.Default)
            .stateIn(scope, SharingStarted.Eagerly, emptyList())

    val vesselsDriving: StateFlow<List<AisDataUi>> = vesselsInInnerRadius
        .map { vessels ->
            vessels
            .filter { vessel -> !vessel.isMoored }
            .sortedBy { vessel -> vessel.distance }
        }.distinctUntilChanged()
                .flowOn(Dispatchers.Default)
                .stateIn(scope, SharingStarted.WhileSubscribed(5000), emptyList())

    val vesselsDrivingGrouped: StateFlow<Map<MovementDirection, List<AisDataUi>>> =
        combine(vesselsDriving, location) {
                searchedVessels, location ->
            searchedVessels
                .groupBy { vessel ->
                    location?.let { l -> vessel.movementDirection(l) } ?: MovementDirection.UNKNOWN
                }
        }.distinctUntilChanged()
            .flowOn(Dispatchers.Default)
            .stateIn(scope, SharingStarted.WhileSubscribed(5000), emptyMap())

    val vesselsMoored: StateFlow<List<AisDataUi>> = vesselsInInnerRadius
        .map { vessels ->
            vessels
                .filter { vessel -> vessel.isMoored }
                .sortedBy { vessel -> vessel.distance }
        }.distinctUntilChanged()
            .flowOn(Dispatchers.Default)
            .stateIn(scope, SharingStarted.WhileSubscribed(5000), emptyList())

    // collects safety data within the outer bounds and combines those with known master data
    val safetyDevices: StateFlow<List<AisDataUi>> =
        combine(
            uiVessels,
            _positionData,
            _masterData,
            _safetyData,
            location
        ) { uiVessels, positionDataMap, masterDataMap, safetyDataMap, location ->
            (safetyDataMap.mapNotNull { (mmsi, safetyData) ->
                val md = masterDataMap[mmsi]
                val pd = positionDataMap[mmsi]
                if (pd == null) { // only process safety messages without existing position data - those are processed in the other loop
                    val distance = location?.distanceTo(safetyData.location) ?: 0.0
                    val mmsiCountryPrefix = fromMmsi(safetyData.mmsi)
                    AisDataUi(
                        messageType = safetyData.messageType,
                        mmsi = safetyData.mmsi,
                        mmsiCountryPrefix = mmsiCountryPrefix,
                        timeUtc = safetyData.timeUtc,
                        location = safetyData.location,
                        imoNumber = if (isValidImo(md?.imoNumber)) md?.imoNumber else null,
                        callSign = md?.callSign,
                        destination = md?.destination,
                        totalLength = md?.totalLength,
                        totalWidth = md?.totalWidth,
                        shipType = ShipType.SAFETY_DEVICE,
                        maximumStaticDraught = md?.maximumStaticDraught,
                        distance = distance,
                        hasSafetyMessage = true,
                        messageId = safetyData.messageId,
                        repeatIndicator = safetyData.repeatIndicator,
                        text = safetyData.text,
                        valid = safetyData.valid,
                    )
                } else {
                    null
                }
            } + uiVessels
                .filter { vessel -> vessel.hasSafetyMessage }
            ).sortedWith(compareByDescending<AisDataUi> { vessel -> vessel.messageSeverity  }
                    .thenBy { vessel -> vessel.distance }
                )
        }.distinctUntilChanged()
            .flowOn(Dispatchers.Default)
            .stateIn(scope, SharingStarted.WhileSubscribed(5000), emptyList())

    // search flow
    private val vesselsSearched: StateFlow<List<AisDataUi>> = state
        .map { it.vesselSearchText }
        .debounce(150.milliseconds)
        .distinctUntilChanged()
        .stateIn(scope, SharingStarted.Eagerly, "")
        .combine(uiVessels) { query, vessels ->
            if (query.isBlank()) {
                emptyList()
            } else {
                val trimmed = query.trim()

                vessels.filter { vessel ->
                    vessel.name.contains(trimmed, ignoreCase = true) ||
                    vessel.callSign?.contains(trimmed, ignoreCase = true) == true ||
                    vessel.shipType.category.name.contains(trimmed, ignoreCase = true) ||
                    vessel.mmsi.toString().contains(trimmed) ||
                    vessel.imoNumber.toString().contains(trimmed)
                }
            }.sortedBy { vessel -> vessel.distance }
        }.distinctUntilChanged()
            .flowOn(Dispatchers.Default)
            .stateIn(scope, SharingStarted.Eagerly, emptyList())

    val vesselsSearchedGrouped: StateFlow<Map<MovementDirection, List<AisDataUi>>> =
        combine(vesselsSearched, location) {
                searchedVessels, location ->
            searchedVessels
                .groupBy { vessel ->
                    location?.let { l -> vessel.movementDirection(l) } ?: MovementDirection.UNKNOWN
                }
        }.distinctUntilChanged()
            .flowOn(Dispatchers.Default)
            .stateIn(scope, SharingStarted.WhileSubscribed(5000), emptyMap())

    val vesselsOnRadar: StateFlow<List<AisDataUi>> =
        combine(
            uiVessels,
            vesselsSearched,
            safetyDevices,
            state.map { it.currentRadarRadius }.distinctUntilChanged(),
            state.map { it.selectedShipCategories }.distinctUntilChanged()
        ) { uiVessels,
            searchedVessels,
            safetyDevices,
            currentRadarRadius,
            selectedShipCategories->

            val categories = selectedShipCategories.keys
            val mode = selectedShipCategories.values.firstOrNull() ?: CategoryMode.unselected
            searchedVessels
                .ifEmpty { uiVessels + safetyDevices }
                .filter { vessel ->
                    // 1. Distanz-Check (gilt immer)
                    val matchesDistance = vessel.distance < currentRadarRadius
                    if (!matchesDistance) return@filter false

                    // 2. Kategorie-Check direkt im selben Durchlauf prüfen
                    if (selectedShipCategories.isNotEmpty()) {
                        when (mode) {
                            CategoryMode.solo -> categories.contains(vessel.shipType.category)
                            CategoryMode.mute -> !categories.contains(vessel.shipType.category)
                            CategoryMode.unselected -> true
                        }
                    } else {
                        true
                    }
                }
        }.distinctUntilChanged()
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
                _state.update { 
                    it.settings?.get<Language>(SK.language)?.also { l -> applyAppLanguage(l.localeCode) }
                    it.copy(
                        isEditingSettings = false,
                        previousSelectedTabIndexes = it.previousSelectedTabIndexes + it.selectedTabIndex,
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
                _state.update {
                    it.copy(
                        isReconnecting = true,
                    )
                }
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
                        tabLabelKeys = action.tabLabels.map { tl -> tl.first },
                        previousSelectedTabIndexes = it.previousSelectedTabIndexes + it.selectedTabIndex,
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
                _state.update { 
                    it.copy(
                        previousSelectedTabIndexes = it.previousSelectedTabIndexes + it.selectedTabIndex,
                        selectedTabIndex = action.index,
                        isEditingSettings = false,
                        hasUnreadSafetyData = hasUnreadSafetyData,
                        isShowInfos = false,
                        uiMessage = null,
                        uiMessageSeverity = null,
                        selectedVessel = null,
                        isShowingRadar = false
                    )
                }
            }
            is ShipermansFriendAction.OnBackButton -> {
                _state.update {
                    it.copy(
                        selectedTabIndex = it.previousSelectedTabIndexes.lastOrNull() ?: 0,
                        isShowingRadar = false,
                        previousSelectedTabIndexes = it.previousSelectedTabIndexes.dropLast(1),
                    )
                }
            }

            //
            // Vessels
            //
            is ShipermansFriendAction.OnShowRadar -> {
                _state.update {
                    it.copy(
                        previousSelectedTabIndexes = it.previousSelectedTabIndexes + it.selectedTabIndex,
                        selectedTabIndex = it.tabLabelKeys.indexOf("radar"),
                        selectedVessel = action.selectedVessel,
                        isShowingRadar = true,
                        previousRadarRadius = it.currentRadarRadius,
                        currentRadarRadius = max(action.selectedVessel?.distance ?: it.currentRadarRadius, it.currentRadarRadius) // ensure that we can see the selected vessel
                    )
                }
            }
            is ShipermansFriendAction.OnShowRadarBack -> {
                _state.update {
                    it.copy(
                        selectedTabIndex = it.previousSelectedTabIndexes.lastOrNull() ?: 0,
                        previousSelectedTabIndexes = it.previousSelectedTabIndexes.dropLast(1),
                        selectedVessel = null,
                        isShowingRadar = false
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
            is ShipermansFriendAction.OnStarredVesselsExport -> {
                exportStarredVessels(action.fileName, action.sink)
            }
            is ShipermansFriendAction.OnSelectedShipCategory -> {
                _state.update { 
                    val selectedShipCategories = if (action.mode == CategoryMode.unselected) {
                        it.selectedShipCategories - action.category
                    } else {
                        val filter = it.selectedShipCategories
                            .filter { (_, mode) -> mode == action.mode }
                        filter + (action.category to action.mode)
                    }
                    it.copy(
                        // Philosophy here is that we can have either muted or soloed categories - not both at the same time.
                        selectedShipCategories = selectedShipCategories
                    )
                }
            }
            is ShipermansFriendAction.OnClearShipCategories -> {
                _state.update {
                    it.copy(
                        selectedShipCategories = mapOf()
                    )
                }
            }
            is ShipermansFriendAction.OnToggleStarredVessel -> {
                maintainStarredVessel(action.vessel, toggleAlert = false)
            }
            is ShipermansFriendAction.OnToggleVesselAlert -> {
                maintainStarredVessel(action.vessel, toggleAlert = true)
            }

            //
            //
            //
            is ShipermansFriendAction.OnReportScreenSize -> {
                _state.update { 
                    it.copy(
                        screenWidth = action.screenWidth,
                        screenHeight = action.screenHeight
                    )
                }
            }
            is ShipermansFriendAction.OnRadarRadiusChange -> {
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

    private fun maintainStarredVessel(vessel: AisDataUi, toggleAlert: Boolean = false) = viewModelScope.launch {
        val mutableStarred = _vesselsStarred.value
        val mutableAlert = state.value.alertVessels.toMutableSet()
        val mmsi = vessel.mmsi
        if (toggleAlert) {
            if (!mutableAlert.contains(mmsi)) {
                mutableAlert.add(mmsi)
                // also bookmark vessel
                if (!mutableStarred.containsKey(mmsi)) {
                    _vesselsStarred.update { current -> current + (mmsi to vessel.copy(timeUtcObserved = KmpOffsetDateTime.now())) }
                    starredVesselRepository.upsertStarredVessel(vessel)
                }
            } else {
                mutableAlert.remove(mmsi)
            }
        } else {
            if (!mutableStarred.containsKey(mmsi)) {
                _vesselsStarred.update { current -> current + (mmsi to vessel.copy(timeUtcObserved = KmpOffsetDateTime.now())) }
                starredVesselRepository.upsertStarredVessel(vessel)
            } else {
                _vesselsStarred.update { current -> current - mmsi }
                starredVesselRepository.deleteStarredVessel(mmsi)
            }
        }

        _state.update {
            it.copy(
                alertVessels = mutableAlert
            )
        }
    }

    private fun importSettings(fileName: String, source: Source) = viewModelScope.launch {
        Logger.i("Importing settings")
        if (fileName.endsWith(".json", ignoreCase = true)) {
            val settingsResult = settingsRepository.importSettings(source)
            if (settingsResult is Result.Success) {
                val settings = settingsResult.data
                startAisClient()
                _state.update {
                    it.copy(
                        settings = settings,
                        isEditingSettings = false,
                        previousSelectedTabIndexes = it.previousSelectedTabIndexes + it.selectedTabIndex,
                        selectedTabIndex = 0,
                        uiMessage = null,
                    )
                }
            } else if (settingsResult is Result.Error) {
                Logger.e("Could not import settings", settingsResult.throwable)
                _state.update {
                    it.copy(
                        uiMessage = settingsResult.error.toUiText(),
                        uiMessageSeverity = Severity.Error,
                        isEditingSettings = false,
                        previousSelectedTabIndexes = it.previousSelectedTabIndexes + it.selectedTabIndex,
                        selectedTabIndex = 0,
                    )
                }
            }
        } else {
            _state.update {
                it.copy(
                    currentProgress = 0.0f,
                    isEditingSettings = false,
                    previousSelectedTabIndexes = it.previousSelectedTabIndexes + it.selectedTabIndex,
                    selectedTabIndex = 0,
                    progressStage = ProgressStage.NONE,
                    uiMessage = UiText.StringResourceId(Res.string.error_local_wrong_filetype),
                    uiMessageSeverity = Severity.Error
                )
            }
        }
    }

    private fun exportSettings(fileName: String, sink: Sink) = viewModelScope.launch {
        Logger.i("Exporting settings")
        if (fileName.endsWith(".json", ignoreCase = true)) {
            val settings = state.value.settings
            if(settings != null) {
                settingsRepository.exportSettings(settings, sink)
                    .onSuccess {
                        _state.update {
                            it.copy(
                                uiMessage = null,
                                isEditingSettings = false,
                                previousSelectedTabIndexes = it.previousSelectedTabIndexes + it.selectedTabIndex,
                                selectedTabIndex = 0,
                            )
                        }
                    }
                    .onError { error, throwable ->
                        Logger.e("Could not export settings", throwable)
                        _state.update {
                            it.copy(
                                uiMessage = error.toUiText(),
                                uiMessageSeverity = Severity.Error,
                                isEditingSettings = false,
                                previousSelectedTabIndexes = it.previousSelectedTabIndexes + it.selectedTabIndex,
                                selectedTabIndex = 0,
                            )
                        }
                    }
            }
        } else {
            _state.update {
                it.copy(
                    currentProgress = 0.0f,
                    isEditingSettings = false,
                    previousSelectedTabIndexes = it.previousSelectedTabIndexes + it.selectedTabIndex,
                    selectedTabIndex = 0,
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
                            isEditingSettings = false,
                            previousSelectedTabIndexes = it.previousSelectedTabIndexes + it.selectedTabIndex,
                            selectedTabIndex = 0,
                        )
                    }
                }
                .onError { error, throwable ->
                    Logger.e("Could not import masterdata", throwable)
                    _state.update {
                        it.copy(
                            uiMessage = error.toUiText(),
                            uiMessageSeverity = Severity.Error,
                            isEditingSettings = false,
                            previousSelectedTabIndexes = it.previousSelectedTabIndexes + it.selectedTabIndex,
                            selectedTabIndex = 0,
                        )
                    }
                }
        } else {
            _state.update {
                it.copy(
                    currentProgress = 0.0f,
                    isEditingSettings = false,
                    previousSelectedTabIndexes = it.previousSelectedTabIndexes + it.selectedTabIndex,
                    selectedTabIndex = 0,
                    progressStage = ProgressStage.NONE,
                    uiMessage = UiText.StringResourceId(Res.string.error_local_wrong_filetype),
                    uiMessageSeverity = Severity.Error
                )
            }
        }
    }

    private fun exportMasterData(fileName: String, sink: Sink) = viewModelScope.launch {
        Logger.i("Exporting masterdata")
        if (fileName.endsWith(".json", ignoreCase = true)) {
            masterDataRepository.exportMasterData(sink)
                .onSuccess {
                    _state.update {
                        it.copy(
                            uiMessage = null,
                            isEditingSettings = false,
                            previousSelectedTabIndexes = it.previousSelectedTabIndexes + it.selectedTabIndex,
                            selectedTabIndex = 0,
                        )
                    }
                }
                .onError { error, throwable ->
                    Logger.e("Could not export masterdata", throwable)
                    _state.update {
                        it.copy(
                            uiMessage = error.toUiText(),
                            uiMessageSeverity = Severity.Error,
                            isEditingSettings = false,
                            previousSelectedTabIndexes = it.previousSelectedTabIndexes + it.selectedTabIndex,
                            selectedTabIndex = 0,
                        )
                    }
                }
        } else {
            _state.update {
                it.copy(
                    currentProgress = 0.0f,
                    isEditingSettings = false,
                    previousSelectedTabIndexes = it.previousSelectedTabIndexes + it.selectedTabIndex,
                    selectedTabIndex = 0,
                    progressStage = ProgressStage.NONE,
                    uiMessage = UiText.StringResourceId(Res.string.error_local_wrong_filetype),
                    uiMessageSeverity = Severity.Error
                )
            }
        }
    }

    private fun exportStarredVessels(fileName: String, sink: Sink) = viewModelScope.launch {
        Logger.i("Exporting starred vessels")
        starredVesselRepository.exportStarredVessels(fileName, sink)
            .onSuccess {
                _vesselsStarred.update { mapOf() }
                _state.update { 
                    it.copy(
                        currentProgress = 0.0f,
                        progressStage = ProgressStage.NONE,
                        uiMessage = null,
                        uiMessageSeverity = null,
                    )
                }
            }
            .onError { local, throwable ->
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
                        Logger.e("Could not safe initial settings", throwable)
                    }
                newSettings
            }

            applyAppLanguage(finalSettings.get<Language>(SK.language)?.localeCode?: Language.EN.localeCode)
            val radiusInner = finalSettings.get<String>(SK.radiusInner)?.notBlank()?.parseDistance() ?: 1000.0

            _state.update {
                it.copy(
                    settings = finalSettings,
                    currentRadarRadius = radiusInner,
                    currentProgress = 0.0f,
                    progressStage = ProgressStage.NONE,
                    uiMessage = null,
                    uiMessageSeverity = null
                )
            }
        } else if (result is Result.Error) {
            Logger.e("Could not load data", result.throwable)
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
                val radiusInner = settings.get<String>(SK.radiusInner)?.parseDistance() ?: 1000.0
                _editedSettings.value = null
                _state.update {
                    it.copy(
                        settings = settings,
                        currentProgress = 0.0f,
                        previousRadarRadius = it.currentRadarRadius,
                        currentRadarRadius = radiusInner,
                        progressStage = ProgressStage.NONE,
                        isEditingSettings = false,
                        previousSelectedTabIndexes = it.previousSelectedTabIndexes + it.selectedTabIndex,
                        selectedTabIndex = 0,
                        uiMessage = null,
                        uiMessageSeverity = null
                    )
                }
            }
            .onError { error, throwable ->
                Logger.e("Could not save settings", throwable)
                _state.update {
                    it.copy(
                        currentProgress = 0.0f,
                        isEditingSettings = false,
                        previousSelectedTabIndexes = it.previousSelectedTabIndexes + it.selectedTabIndex,
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
                    isReconnecting = false,
                    isEditingSettings = false,
                    previousSelectedTabIndexes = it.previousSelectedTabIndexes + it.selectedTabIndex,
                    selectedTabIndex = 0,
                    progressStage = ProgressStage.NONE,
                    uiMessage = DataError.Remote.CONNECTION_ERROR.toUiText(),
                    uiMessageSeverity = Severity.Error
                )
            }
        }
    }
}
