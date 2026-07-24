package de.visualdigits.shipermansfriend.presentation.page.search

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
fun VesselsTabSearch(
    viewModel: ShipermansFriendViewModel,
    state: ShipermansFriendState,
    vesselsStarred: Map<Long, AisDataUi>,
    vesselsWarned:  Map<Long, AisDataUi>,
    vesselsAlerted:  Map<Long, AisDataUi>,
    alertVessels: Set<Long>,
    sizeFactor: Float,
    platformType: PlatformType,
    location: Location,
    onCommonAction: (CommonAction) -> Unit,
    player: GadulkaPlayer,
    audioStorage: AudioStorage,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val searchedVessels by viewModel.vesselsSearched.collectAsStateWithLifecycle()
    val currentTime = KmpOffsetDateTime.now()

    VesselsStatic(
        vesselsMode = VesselsMode.SEARCH,
        state = state,
        onAction = onAction,
        viewModel = viewModel,
        sizeFactor = sizeFactor,
        vessels = searchedVessels,
        vesselsStarred = vesselsStarred,
        vesselsWarned = vesselsWarned,
        vesselsInInnerRadius = vesselsAlerted,
        alertVessels = alertVessels,
        platformType = platformType,
        onCommonAction = onCommonAction,
        currentTime = currentTime,
        player = player,
        audioStorage = audioStorage,
        location = location
    )
}
