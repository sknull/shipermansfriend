package de.visualdigits.shipermansfriend.presentation.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipCategory
import de.visualdigits.shipermansfriend.domain.model.settings.Settings
import de.visualdigits.shipermansfriend.domain.model.type.CategoryMode
import de.visualdigits.shipermansfriend.domain.model.type.Language
import de.visualdigits.shipermansfriend.domain.model.type.ProgressStage

@Stable
data class ShipermansFriendState(

    val maxImageSize: Int? = null,

    val settings: Settings? = null,

    val language: Language = Language.EN,

    val screenWidth: Dp = Dp.Unspecified,
    val screenHeight: Dp = Dp.Unspecified,

    val previousSelectedTabIndexes: List<Int> = listOf(),
    val selectedTabIndex: Int = 0,
    val tabLabels: List<Pair<String, UiText>> = listOf(),
    val tabLabelKeys: List<String> = listOf(),

    val isShowInfos: Boolean = false,
    val isEditingSettings: Boolean = false,
    val hasUnreadSafetyData: Boolean = false,
    val hasUnseenAlertVessels: Boolean = false,

    val vesselSearchText: String? = null,
    val isVesselSearchActive: Boolean = false,
    val filteredVessels: List<AisDataUi> = listOf(),

    val uiMessage: UiText? = null,
    val uiMessageSeverity: Severity? = null,

    val selectedShipCategories: Map<ShipCategory, CategoryMode> = mapOf(),

    val currentProgress: Float = 0.0f,
    val progressStage: ProgressStage = ProgressStage.NONE,

    val collapsibleState: Map<String, Boolean> = mapOf(),

    val isEditMode: Boolean = false,

    val isShowingRadar: Boolean = false,
    val selectedVessel: AisDataUi? = null,

    val currentRadarRadius: Double = 0.0,
    val previousRadarRadius: Double = 0.0,

    val isReconnecting: Boolean = false,

    val starredVessels: Map<Long, AisDataUi> = mapOf(),

    val alertVessels: Set<Long> = setOf()
)
