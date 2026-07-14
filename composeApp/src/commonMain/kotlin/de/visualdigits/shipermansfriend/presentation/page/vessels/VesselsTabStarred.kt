package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.presentation.model.CommonAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.model.VesselsMode


@Composable
fun VesselsTabStarred(
    viewModel: ShipermansFriendViewModel,
    state: ShipermansFriendState,
    sizeFactor: Float,
    platformType: PlatformType,
    location: Location,
    onCommonAction: (CommonAction) -> Unit,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val vesselsStarred by viewModel.vesselsStarredGrouped.collectAsStateWithLifecycle()
    val currentTime = KmpOffsetDateTime.now()

    VesselsDynamic(
        viewModel = viewModel,
        state = state,
        sizeFactor = sizeFactor,
        vessels = vesselsStarred,
        platformType = platformType,
        vesselsMode = VesselsMode.STARRED,
        onCommonAction = onCommonAction,
        currentTime = currentTime,
        location = location,
        onAction = onAction
    )
}
