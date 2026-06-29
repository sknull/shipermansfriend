package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi

@Composable
fun RadarPortrait(
    location: Location,
    currentRadarRadius: Double,
    selectedVessel: AisDataUi,
    vessels: List<AisDataUi>,
    safetyDevices: List<AisDataUi>,
    activeHoverVesselState: MutableState<List<AisDataUi>>,
    imageHeading: ImageBitmap,
    colorBackground: Color,
    colorGrid: Color
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            RadarBox(
                location = location,
                currentRadarRadius = currentRadarRadius,
                selectedVessel = selectedVessel,
                vessels = vessels,
                safetyDevices = safetyDevices,
                setActiveHoverName = { activeHoverVessel ->
                    activeHoverVesselState.value = activeHoverVessel
                },
                imageHeading = imageHeading,
                colorBackground = colorBackground,
                colorGrid = colorGrid
            )

            HoveredVesselBox(
                activeHoverVesselState = activeHoverVesselState
            )
        }

        LegendBox(
        )
    }
}
