package de.visualdigits.shipermansfriend.presentation.model

import androidx.compose.runtime.Stable
import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.settings.Settings
import de.visualdigits.shipermansfriend.domain.model.type.Language
import de.visualdigits.shipermansfriend.domain.model.type.ProgressStage

@Stable
data class ShipermansFriendState(

    val maxImageSize: Int? = null,

    val settings: Settings? = null,

    val language: Language = Language.EN,

    val selectedTabIndex: Int = 0,
    val tabLabels: List<Pair<String, UiText>> = listOf(),

    val isShowInfos: Boolean = false,
    val isEditingSettings: Boolean = false,

    val vesselSearchText: String? = null,
    val isVesselSearchActive: Boolean = false,
    val filteredVessels: List<AisDataUi> = listOf(),

    val uiMessage: UiText? = null,
    val uiMessageSeverity: Severity? = null,

    val currentProgress: Float = 0.0f,
    val progressStage: ProgressStage = ProgressStage.NONE,

    val collapsibleState: Map<String, Boolean> = mapOf(),

    val isEditMode: Boolean = false,

    val vessels: List<AisDataUi> = listOf(),
    val selectedVessel: AisDataUi? = null
)
