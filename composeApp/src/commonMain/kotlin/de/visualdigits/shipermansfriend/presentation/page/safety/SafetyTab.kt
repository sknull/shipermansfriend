package de.visualdigits.shipermansfriend.presentation.page.safety

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.presentation.components.PlatformVerticalScrollbarBox
import de.visualdigits.common.presentation.model.PlatformScrollbarStyle
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.page.vessels.LocationBar
import de.visualdigits.shipermansfriend.presentation.page.vessels.VesselCard
import de.visualdigits.shipermansfriend.presentation.style.gap

@Composable
fun SafetyTab(
    state: ShipermansFriendState,
    viewModel: ShipermansFriendViewModel,
    sizeFactor: Float,
    platformType: PlatformType,
    onAction: (ShipermansFriendAction) -> Unit
) {

    val uiVesselsList by viewModel.uiVessels.collectAsStateWithLifecycle()
    val vessels by remember {
        derivedStateOf { uiVesselsList
            .filter { it.hasSafetyMessage }
            .sortedWith(compareByDescending<AisDataUi> { ud -> ud.messageSeverity.ordinal }
                .thenBy { ud -> ud.distance }
            )
        }
    }
    val safetyDevices by viewModel.safetyDevices.collectAsStateWithLifecycle()
    val innerRadius by viewModel.innerRadius.collectAsStateWithLifecycle()

    val allVessels by remember {
        derivedStateOf { vessels + safetyDevices }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MaterialTheme.shapes.gap),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
    ) {
        LocationBar(
            viewModel = viewModel,
            state = state,
            sizeFactor = sizeFactor,
            currentRadarRadius = innerRadius,
            vesselNumber = allVessels.size,
            onAction = viewModel::onAction
        )

        PlatformVerticalScrollbarBox(
            modifier = Modifier
                .weight(1f)
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
                hoverColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        ) {
            if (allVessels.isNotEmpty()) {
                allVessels.map { vessel ->
                    Pair("safetyMessage_${vessel.timeUtc}", @Composable {
                        key("safetyMessage_${vessel.timeUtc}") {
                            VesselCard(
                                state = state,
                                viewModel = viewModel,
                                vessels = safetyDevices,
                                selectedVessel = vessel,
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
