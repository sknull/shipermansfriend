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
import de.visualdigits.shipermansfriend.domain.mapper.toAisDataUi
import de.visualdigits.shipermansfriend.domain.mapper.toPhotoProtocolEntry
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.AisStreamState
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType.Companion.SAFETY_DATA
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.ReceivingDataState
import de.visualdigits.shipermansfriend.domain.model.errorhandling.DataError
import de.visualdigits.shipermansfriend.domain.model.errorhandling.toUiText
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi.Companion.isValidImo
import de.visualdigits.shipermansfriend.domain.model.geodata.PositionData
import de.visualdigits.shipermansfriend.domain.model.geodata.SafetyData
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType
import de.visualdigits.shipermansfriend.domain.model.geodata.mmsi.MasterData
import de.visualdigits.shipermansfriend.domain.model.geodata.mmsi.MmsiPrefix.Companion.fromMmsi
import de.visualdigits.shipermansfriend.domain.model.settings.SK
import de.visualdigits.shipermansfriend.domain.model.settings.Settings
import de.visualdigits.shipermansfriend.domain.model.type.CategoryMode
import de.visualdigits.shipermansfriend.domain.model.type.Language
import de.visualdigits.shipermansfriend.domain.model.type.ProgressStage
import de.visualdigits.shipermansfriend.domain.repository.MasterDataRepository
import de.visualdigits.shipermansfriend.domain.repository.PhotoProtocolRepository
import de.visualdigits.shipermansfriend.domain.repository.SettingsRepository
import de.visualdigits.shipermansfriend.domain.util.formatDistance
import de.visualdigits.shipermansfriend.domain.util.formatSpeed
import de.visualdigits.shipermansfriend.domain.util.notBlank
import de.visualdigits.shipermansfriend.domain.util.parseDistance
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
    private val photoProtocolRepository: PhotoProtocolRepository,
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

    val location: StateFlow<Location?> = aisStreamClient._location.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val connectivityMode = aisStreamClient._connectivityMode.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ConnectivityMode.disconnected)

    val aisStreamState = aisStreamClient._aisStreamState.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AisStreamState.Down)

    val receivingDataState = aisStreamClient._receivingDataState.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ReceivingDataState.disconnected)

    val lastLocationUpdateDuration = aisStreamClient._lastLocationUpdateDuration.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.seconds)

    val innerRadius = aisStreamClient._innerRadius.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

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
            photoProtocolRepository.getAllPhotoProtocolEntries()
                .onSuccess { photoProtocol ->
                    _state.update {
                        it.copy(
                            photoProtocol = photoProtocol
                                .map { ppe -> ppe.toAisDataUi() }
                                .associateBy { ppe -> ppe.mmsi }
                        )
                    }
                    Logger.i("Restored ${photoProtocol.size} photoprotocol entries from database.")
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
                "radar" to UiText.DynamicString(""),
            )
        ))

        startAisClient()

        // message collection loop
        scope.launch {
            aisStreamClient.messages
                .collect { message ->
                    aisStreamClient._receivingDataState.update { ReceivingDataState.receivingData }
                    _state.update {
                        it.copy(
                            isReconnecting = false
                        )
                    }

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
                                    Logger.e("Could not insert master data for mmsi '${message.mmsi}'", throwable)
                                }
                        }
                        // collects position data within the inner bounds
                        is PositionData -> {
                            val hasSafetyMessage = (aisStreamClient._outerBoundingBox.value?.let { bb -> message.location.isInBoundingBox(bb) } == true) &&
                                    _safetyData.value.containsKey(message.mmsi)
                            val isWithinInnerBounds = aisStreamClient._innerBoundingBox.value?.let { bb -> message.location.isInBoundingBox(bb) } == true
                            if (isWithinInnerBounds || hasSafetyMessage) {
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
                val md = masterDataMap[mmsi]
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
                        imoNumber = if (isValidImo(md?.imoNumber)) md?.imoNumber else 0,
                        callSign = md?.callSign,
                        destination = md?.destination,
                        totalLength = md?.totalLength,
                        totalWidth = md?.totalWidth,
                        shipType = ShipType.SAFETY_DEVICE,
                        maximumStaticDraught = md?.maximumStaticDraught,
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
                        md?.shipType ?: ShipType.Unknown_0
                    }
                    AisDataUi(
                        messageType = positionData.messageType,
                        name = positionData.name,
                        mmsi = positionData.mmsi,
                        mmsiCountryPrefix = fromMmsi(positionData.mmsi),
                        timeUtc = positionData.timeUtc,
                        location = positionData.location,
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
                    .thenByDescending { ud -> ud.messageSeverity.ordinal }
                    .thenBy { ud -> ud.distance }
                )
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
            is ShipermansFriendAction.OnAddVesselToPhotoProtocol -> {
                maintainPhotoProtocol(location.value,  action.vessel)
            }
            is ShipermansFriendAction.OnPhotoProtocolExport -> {
                exportPhotoProtocol(action.fileName, action.sink)
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

    private fun maintainPhotoProtocol(location: Location?, vessel: AisDataUi) = viewModelScope.launch {
        _state.update { 
            val mutableCopy = it.photoProtocol.toMutableMap()
            val mmsi = vessel.mmsi
            if (mutableCopy.containsKey(mmsi)) {
                mutableCopy.remove(mmsi)
                photoProtocolRepository.deletePhotoProtocolEntry(mmsi)
            } else {
                vessel.timeUtcObserved = KmpOffsetDateTime.now()
                mutableCopy[mmsi] = vessel
                val entry = vessel.toPhotoProtocolEntry(location)
                photoProtocolRepository.upsertPhotoProtocolEntryEntity(entry)
            }
            it.copy(
                photoProtocol = mutableCopy
            )
        }
    }

    private fun updateRadarRadius(radius: Double) {
        location.value?.also { location ->
            val boundingBox = location.calculateBoundingBox(radius)
            _positionData.update { current ->
                current
                    .toMutableMap()
                    .filter { (_, positionData) ->
                        positionData.location.isInBoundingBox(boundingBox)
                    }
            }
            _safetyData.update { current ->
                current
                    .toMutableMap()
                    .filter { (_, positionData) ->
                        positionData.location.isInBoundingBox(boundingBox)
                    }
            }
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

    private fun exportPhotoProtocol(fileName: String, sink: Sink) = viewModelScope.launch {
        Logger.i("Exporting photo protocol")
        photoProtocolRepository.exportPhotoProtocolEntries(fileName, sink)
            .onSuccess {
                _state.update { 
                    it.copy(
                        currentProgress = 0.0f,
                        progressStage = ProgressStage.NONE,
                        uiMessage = null,
                        uiMessageSeverity = null,
                        photoProtocol = mapOf()
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
            val radarRadius = finalSettings.get<String>(SK.radiusInner)?.notBlank()?.parseDistance() ?: 1000.0

            _state.update {
                it.copy(
                    settings = finalSettings,
                    currentRadarRadius = radarRadius,
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
                val radarRadius = settings.get<String>(SK.radiusInner)?.parseDistance() ?: 1000.0
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
