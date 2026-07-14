package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.runtime.Composable
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.presentation.model.CommonAction
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.MovementDirection
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.model.VesselsMode


@Composable
fun VesselsTabAlerted(
    viewModel: ShipermansFriendViewModel,
    state: ShipermansFriendState,
    vesselsStarred: Map<Long, AisDataUi>,
    sizeFactor: Float,
    platformType: PlatformType,
    location: Location,
    vessels: Map<MovementDirection, List<AisDataUi>>,
    onCommonAction: (CommonAction) -> Unit,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val currentTime = KmpOffsetDateTime.now()

    VesselsDynamic(
        vesselsMode = VesselsMode.ALERTED,
        state = state,
        onAction = onAction,
        viewModel = viewModel,
        sizeFactor = sizeFactor,
        vessels = vessels,
        vesselsStarred = vesselsStarred,
        platformType = platformType,
        onCommonAction = onCommonAction,
        currentTime = currentTime,
        location = location
    )
}
