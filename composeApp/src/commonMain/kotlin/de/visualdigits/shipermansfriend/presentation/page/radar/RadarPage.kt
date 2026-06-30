package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    isLandscape: Boolean,
    onAction: (ShipermansFriendAction) -> Unit
) {

    val radiusInner = state.settings?.get<String>(SK.radiusInner)?.parseDistance() ?: 1000.0

    val selectedVessel = state.selectedVessel!!
    val activeHoverVesselState = remember { mutableStateOf<List<AisDataUi>>(emptyList()) }

    val vessels by viewModel.uiVessels.collectAsStateWithLifecycle()
    val safetyDevices by viewModel.safetyDevices.collectAsStateWithLifecycle()

    val imageHeading = imageResource(Res.drawable.image_direction_white)
    val colorBackground = Color(0xFF004711)
    val colorGrid = Color(0xFF00FF00)
    val currentRadarRadius = state.currentRadarRadius

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
            vesselNumber = vessels.size,
            safetyDeviceNumber = safetyDevices.size,
            onAction = onAction
        )

        if (isLandscape) {
            RadarLandscape(
                location = location,
                currentRadarRadius = currentRadarRadius,
                selectedVessel = selectedVessel,
                vessels = vessels,
                safetyDevices = safetyDevices,
                activeHoverVesselState = activeHoverVesselState,
                imageHeading = imageHeading,
                colorBackground = colorBackground,
                colorGrid = colorGrid
            )
        } else {
            RadarPortrait(
                location = location,
                currentRadarRadius = currentRadarRadius,
                selectedVessel = selectedVessel,
                vessels = vessels,
                safetyDevices = safetyDevices,
                activeHoverVesselState = activeHoverVesselState,
                imageHeading = imageHeading,
                colorBackground = colorBackground,
                colorGrid = colorGrid
            )
        }
    }
}
