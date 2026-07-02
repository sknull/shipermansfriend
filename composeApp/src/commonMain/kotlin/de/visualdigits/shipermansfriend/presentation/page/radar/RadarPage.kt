package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.image_direction_white
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.settings.SK
import de.visualdigits.shipermansfriend.domain.util.parseDistance
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.imageResource

@Composable
fun RadarPage(
    viewModel: ShipermansFriendViewModel,
    state: ShipermansFriendState,
    location: Location,
    onAction: (ShipermansFriendAction) -> Unit
) {

    val radiusInner = state.settings?.get<String>(SK.radiusInner)?.parseDistance() ?: 1000.0

    val selectedVessel = state.selectedVessel!!
    val activeHoverVesselState = remember { mutableStateOf<List<AisDataUi>>(emptyList()) }

    val vessels by viewModel.uiVessels.collectAsStateWithLifecycle()
    val searchedVessels by viewModel.searchedVessels.collectAsStateWithLifecycle()
    val safetyDevices by viewModel.safetyDevices.collectAsStateWithLifecycle()

    var selectedShipCategory by remember { mutableStateOf(state.selectedShipCategory) }
    LaunchedEffect(state.selectedShipCategory) {
        selectedShipCategory = state.selectedShipCategory
    }

    val imageHeading = imageResource(Res.drawable.image_direction_white)
    val colorBackground = Color(0xFF004711)
    val colorGrid = Color(0xFF00FF00)
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
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
    ) {

        RadarPageMenuBar(
            state = state,
            currentRadarRadius = currentRadarRadius,
            setCurrentRadarRadius = { radius ->
                onAction(ShipermansFriendAction.OnRadarRadiusChange(radius))
            },
            radiusInner = radiusInner,
            selectedVessel = selectedVessel,
            vesselNumber = vesselsOnRadar.size,
            safetyDeviceNumber = safetyDevices.size,
            onAction = onAction
        )

        if (state.screenWidth > state.screenHeight) {
            RadarLandscape(
                state = state,
                selectedShipCategory = selectedShipCategory,
                location = location,
                currentRadarRadius = currentRadarRadius,
                selectedVessel = selectedVessel,
                vessels = vesselsOnRadar,
                activeHoverVesselState = activeHoverVesselState,
                imageHeading = imageHeading,
                colorBackground = colorBackground,
                colorGrid = colorGrid,
                onAction = onAction
            )
        } else {
            RadarPortrait(
                state = state,
                selectedShipCategory = selectedShipCategory,
                location = location,
                currentRadarRadius = currentRadarRadius,
                selectedVessel = selectedVessel,
                vessels = vesselsOnRadar,
                activeHoverVesselState = activeHoverVesselState,
                imageHeading = imageHeading,
                colorBackground = colorBackground,
                colorGrid = colorGrid,
                onAction = onAction
            )
        }
    }
}
