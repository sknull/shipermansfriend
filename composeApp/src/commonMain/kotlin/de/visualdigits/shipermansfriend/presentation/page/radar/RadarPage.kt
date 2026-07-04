package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.style.RadarBackground
import de.visualdigits.shipermansfriend.presentation.style.gap

@Composable
fun RadarPage(
    viewModel: ShipermansFriendViewModel,
    state: ShipermansFriendState,
    sizeFactor: Float,
    location: Location,
    onAction: (ShipermansFriendAction) -> Unit
) {

    val selectedVessel = state.selectedVessel!!
    val activeHoverVesselState = remember { mutableStateOf<List<AisDataUi>>(emptyList()) }

    val vessels by viewModel.uiVessels.collectAsStateWithLifecycle()
    val searchedVessels by viewModel.searchedVessels.collectAsStateWithLifecycle()
    val safetyDevices by viewModel.safetyDevices.collectAsStateWithLifecycle()

    var selectedShipCategory by remember { mutableStateOf(state.selectedShipCategory) }
    LaunchedEffect(state.selectedShipCategory) {
        selectedShipCategory = state.selectedShipCategory
    }

    val currentRadarRadius = state.currentRadarRadius
    val currentBoundingBox = location.calculateBoundingBox(currentRadarRadius)

    val vesselsOnRadar = searchedVessels
        .ifEmpty { vessels + safetyDevices }
        .filter { vessel ->
                vessel.location.isInBoundingBox(currentBoundingBox) &&
                    (selectedShipCategory == null || selectedShipCategory == vessel.shipType?.category)
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RadarBackground)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
    ) {


        if (state.screenWidth > state.screenHeight) {
            RadarLandscape(
                state = state,
                sizeFactor = sizeFactor,
                selectedShipCategory = selectedShipCategory,
                location = location,
                currentRadarRadius = currentRadarRadius,
                selectedVessel = selectedVessel,
                vessels = vesselsOnRadar,
                safetyDevices = safetyDevices,
                activeHoverVesselState = activeHoverVesselState,
                onAction = onAction
            )
        } else {
            RadarPortrait(
                state = state,
                sizeFactor = sizeFactor,
                selectedShipCategory = selectedShipCategory,
                location = location,
                currentRadarRadius = currentRadarRadius,
                selectedVessel = selectedVessel,
                vessels = vesselsOnRadar,
                safetyDevices = safetyDevices,
                activeHoverVesselState = activeHoverVesselState,
                onAction = onAction
            )
        }
    }
}
