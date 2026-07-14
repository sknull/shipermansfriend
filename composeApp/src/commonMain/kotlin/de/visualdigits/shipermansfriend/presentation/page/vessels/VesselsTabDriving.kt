package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.presentation.model.CommonAction
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.model.VesselsMode

@Composable
fun VesselsTabDriving(
    viewModel: ShipermansFriendViewModel,
    state: ShipermansFriendState,
    vesselsStarred: Map<Long, AisDataUi>,
    sizeFactor: Float,
    platformType: PlatformType,
    location: Location,
    onCommonAction: (CommonAction) -> Unit,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val vessels by viewModel.vesselsDrivingGrouped.collectAsStateWithLifecycle()
    val currentTime = KmpOffsetDateTime.now()

    VesselsDynamic(
        vesselsMode = VesselsMode.DRIVING,
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
