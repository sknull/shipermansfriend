package de.visualdigits.shipermansfriend.presentation.page.safety

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
import de.visualdigits.shipermansfriend.presentation.page.vessels.VesselsStatic

@Composable
fun VesselsTabSafety(
    viewModel: ShipermansFriendViewModel,
    state: ShipermansFriendState,
    sizeFactor: Float,
    platformType: PlatformType,
    location: Location,
    onCommonAction: (CommonAction) -> Unit,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val vessels by viewModel.safetyDevices.collectAsStateWithLifecycle()
    val currentTime = KmpOffsetDateTime.now()

    VesselsStatic(
        viewModel = viewModel,
        state = state,
        sizeFactor = sizeFactor,
        vessels = vessels,
        platformType = platformType,
        vesselsMode = VesselsMode.SAFETY,
        onCommonAction = onCommonAction,
        currentTime = currentTime,
        location = location,
        onAction = onAction
    )
}
