package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.presentation.components.PlatformVerticalScrollbarBox
import de.visualdigits.common.presentation.model.CommonAction
import de.visualdigits.common.presentation.model.PlatformScrollbarStyle
import de.visualdigits.shipermansfriend.di.AudioStorage
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.MovementDirection
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.model.VesselsMode
import de.visualdigits.shipermansfriend.presentation.page.search.VesselSearchBar
import de.visualdigits.shipermansfriend.presentation.style.gap
import eu.iamkonstantin.kotlin.gadulka.GadulkaPlayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VesselsDynamic(
    vesselsMode: VesselsMode,
    state: ShipermansFriendState,
    onAction: (ShipermansFriendAction) -> Unit,
    viewModel: ShipermansFriendViewModel,
    sizeFactor: Float,
    vessels: Map<MovementDirection, List<AisDataUi>>,
    vesselsStarred: Map<Long, AisDataUi>,
    vesselsWarned:  Map<Long, AisDataUi>,
    vesselsInInnerRadius:  Map<Long, AisDataUi>,
    alertVessels: Set<Long>,
    platformType: PlatformType,
    onCommonAction: (CommonAction) -> Unit,
    currentTime: KmpOffsetDateTime,
    player: GadulkaPlayer,
    audioStorage: AudioStorage,
    location: Location
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MaterialTheme.shapes.gap),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
    ) {
        if (vesselsMode == VesselsMode.SEARCH) {
            VesselSearchBar(
                modifier = Modifier
                    .height(30.dp)
                    .padding(0.dp),
                state = state,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                onAction = onAction
            )
        } else {
            LocationBar(
                viewModel = viewModel,
                state = state,
                sizeFactor = sizeFactor,
                vesselNumber = vessels.values.sumOf { l -> l.size },
                onAction = viewModel::onAction
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
        ) {
            if (vessels.isNotEmpty()) {
                val movementDirections = MovementDirection.entries
                    .filter { d -> vessels.containsKey(d) }

                var expanded by remember { mutableStateOf(false) }
                var currentMovementDirection by remember { mutableStateOf(movementDirections.firstOrNull() ?: MovementDirection.UNKNOWN)}

                MovementDirectionSelector(
                    expanded = expanded,
                    movementDirections = movementDirections,
                    currentMovementDirection = currentMovementDirection,
                    setExpanded = { e -> expanded = e },
                    setCurrentMovementDirection = { md -> currentMovementDirection = md }
                )

                PlatformVerticalScrollbarBox(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = if (platformType == PlatformType.jvm) 20.dp else 0.dp),
                    scrollbarModifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .width(10.dp)
                        .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)),
                    platformType = platformType,
                    scrollbarStyle = PlatformScrollbarStyle(
                        minimalHeight = 16.dp,
                        thickness = 8.dp,
                        shape = RoundedCornerShape(4.dp),
                        hoverDurationMillis = 300,
                        unhoverColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                        hoverColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    ),
                    scrollbarId = "vessels_driving",
                    scrollPosition = viewModel.scrollPosition,
                    onCommonAction = onCommonAction
                ) {
                    val directionVessels = vessels[currentMovementDirection]
                    if (directionVessels?.isNotEmpty() == true) {
                        directionVessels.map { vessel ->
                            Pair("vessel_${vesselsMode.name}_${currentMovementDirection.name}_${vessel.mmsi}", @Composable {
                                key("vessel_${vesselsMode.name}_${currentMovementDirection.name}_${vessel.mmsi}") {
                                    VesselCard(
                                        state = state,
                                        sizeFactor = sizeFactor,
                                        vessel = vessel,
                                        vesselStarred = vesselsStarred.contains(vessel.mmsi),
                                        vesselWarned = vesselsWarned.contains(vessel.mmsi),
                                        vesselInInnerRadius = vesselsInInnerRadius.contains(vessel.mmsi),
                                        vesselInAlertList = alertVessels.contains(vessel.mmsi),
                                        currentTime = currentTime,
                                        location = location,
                                        player = player,
                                        audioStorage = audioStorage,
                                        onAction = onAction
                                    )
                                }
                            })
                        }
                    } else {
                        listOf()
                    }
                }
            }
        }
    }
}
