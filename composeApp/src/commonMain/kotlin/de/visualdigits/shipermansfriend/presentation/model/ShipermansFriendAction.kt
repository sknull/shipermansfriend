package de.visualdigits.shipermansfriend.presentation.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import de.visualdigits.common.domain.model.ui.KeyValue
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipCategory
import de.visualdigits.shipermansfriend.domain.model.settings.Settings
import de.visualdigits.shipermansfriend.domain.model.type.CategoryMode
import de.visualdigits.shipermansfriend.domain.model.type.Language
import kotlinx.io.Sink
import kotlinx.io.Source

sealed interface ShipermansFriendAction {

    //
    // Settings
    //

    @Immutable
    data class OnEditSettingsClick(
        val isEditingSettings: Boolean
    ) : ShipermansFriendAction

    @Immutable
    data class OnSettingsValueChanged(
        val keyValue: KeyValue,
    ): ShipermansFriendAction

    @Immutable
    class OnEditSettingsCancelClick : ShipermansFriendAction

    @Immutable
    data class OnSettingsImport(
        val fileName: String,
        val source: Source
    ): ShipermansFriendAction

    @Immutable
    data class OnSettingsExport(
        val fileName: String,
        val sink: Sink
    ): ShipermansFriendAction

    @Immutable
    class OnSaveSettingsClick : ShipermansFriendAction

    @Immutable
    data class OnShowInfosClick(
        val isShowInfos: Boolean
    ) : ShipermansFriendAction

    @Immutable
    data class UpdateMaxImageSize(
        val settings: Settings?,
        val maxImageSize: Int
    ) : ShipermansFriendAction

    //
    // Masterdata
    //

    @Immutable
    data class OnMasterDataImport(
        val fileName: String,
        val source: Source
    ): ShipermansFriendAction

    @Immutable
    data class OnMasterDataExport(
        val fileName: String,
        val sink: Sink
    ): ShipermansFriendAction

    //
    // Vessels
    //
    @Immutable
    data class OnShowRadar(
        val selectedVessel: AisDataUi? = null
    ): ShipermansFriendAction

    @Immutable
    class OnShowRadarBack: ShipermansFriendAction

    @Immutable
    data class OnVesselSearchExpandStateChanged(
        val expanded: Boolean
    ): ShipermansFriendAction

    @Immutable
    data class OnVesselSearchTextChanged(
        val text: String
    ): ShipermansFriendAction

    @Immutable
    data class OnAddVesselToPhotoProtocol(
        val vessel: AisDataUi
    ): ShipermansFriendAction

    @Immutable
    data class OnPhotoProtocolExport(
        val fileName: String,
        val sink: Sink
    ): ShipermansFriendAction

    @Immutable
    data class OnSelectedShipCategory(
        val category: ShipCategory,
        val mode: CategoryMode
    ): ShipermansFriendAction

    @Immutable
    class OnClearShipCategories: ShipermansFriendAction

    //
    // Tabs
    //
    @Immutable
    data class OnTabSelected(
        val index: Int
    ): ShipermansFriendAction

    @Immutable
    data class OnInitializeTabs(
        val tabLabels: List<Pair<String, UiText>>
    ): ShipermansFriendAction

    @Immutable
    class OnBackButton : ShipermansFriendAction

    //
    //
    //
    @Immutable
    data class OnReportScreenSize(
        val screenWidth: Dp,
        val screenHeight: Dp,
    ): ShipermansFriendAction

    @Immutable
    data class OnRadarRadiusChange(
        val radius: Double
    ): ShipermansFriendAction

    @Immutable
    data class OnCollapsibleStateChange(
        val id: String,
        val isExpanded: Boolean
    ): ShipermansFriendAction

    @Immutable
    data class OnLanguageSelected(
        val language: Language,
    ): ShipermansFriendAction

    @Immutable
    class OnReconnect: ShipermansFriendAction
}
