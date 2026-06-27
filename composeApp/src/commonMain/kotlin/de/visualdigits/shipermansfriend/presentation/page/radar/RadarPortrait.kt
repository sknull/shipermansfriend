package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi

@Composable
fun RadarPortrait(
    location: Location,
    currentRadarRadius: Double,
    selectedVessel: AisDataUi,
    vessels: List<AisDataUi>,
    activeHoverVesselState: MutableState<AisDataUi?>,
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
                activeHoverVesselState = activeHoverVesselState,
                setActiveHoverName = { activeHoverVessel ->
                    activeHoverVesselState.value = activeHoverVessel
                },
                imageHeading = imageHeading,
                colorBackground = colorBackground,
                colorGrid = colorGrid
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            HoveredVesselBox(
                activeHoverVesselState = activeHoverVesselState
            )
        }

        LegendBox(
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
