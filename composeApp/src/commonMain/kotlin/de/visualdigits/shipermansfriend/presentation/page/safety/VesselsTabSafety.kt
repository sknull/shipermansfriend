package de.visualdigits.shipermansfriend.presentation.page.safety

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.presentation.model.CommonAction
import de.visualdigits.shipermansfriend.di.AudioStorage
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.model.VesselsMode
import de.visualdigits.shipermansfriend.presentation.page.vessels.VesselsStatic
import eu.iamkonstantin.kotlin.gadulka.GadulkaPlayer

@Composable
fun VesselsTabSafety(
    viewModel: ShipermansFriendViewModel,
    state: ShipermansFriendState,
    vesselsStarred: Map<Long, AisDataUi>,
    sizeFactor: Float,
    platformType: PlatformType,
    location: Location,
    player: GadulkaPlayer,
    audioStorage: AudioStorage,
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
        vesselsStarred = vesselsStarred,
        platformType = platformType,
        vesselsMode = VesselsMode.SAFETY,
        onCommonAction = onCommonAction,
        currentTime = currentTime,
        location = location,
        player = player,
        audioStorage = audioStorage,
        onAction = onAction
    )
}
