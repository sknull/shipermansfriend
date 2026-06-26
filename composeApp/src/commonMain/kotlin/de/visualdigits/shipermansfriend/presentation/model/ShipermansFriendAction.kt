package de.visualdigits.shipermansfriend.presentation.model

import androidx.compose.runtime.Immutable
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.model.ui.KeyValue
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.settings.Settings
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
    data class OnShowRadar(
        val location: Location?,
        val vessels: List<AisDataUi>,
        val selectedVessel: AisDataUi
    ): ShipermansFriendAction

    class OnShowRadarBack: ShipermansFriendAction

    //
    // Tabs
    //
    data class OnTabSelected(
        val index: Int
    ): ShipermansFriendAction

    data class OnInitializeTabs(
        val tabLabels: List<Pair<String, UiText>>
    ): ShipermansFriendAction

    //
    //
    //
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
