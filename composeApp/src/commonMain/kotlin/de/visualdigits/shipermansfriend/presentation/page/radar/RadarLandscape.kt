package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.style.gap

@Composable
fun RadarLandscape(
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
    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                RadarBox(
                    location = location,
                    currentRadarRadius = currentRadarRadius,
                    selectedVessel = selectedVessel,
                    vessels = vessels,
                    safetyDevices = safetyDevices,
                    setActiveHoverName = { activeHoverName ->
                        activeHoverVesselState.value = activeHoverName
                    },
                    imageHeading = imageHeading,
                    colorBackground = colorBackground,
                    colorGrid = colorGrid
                )

                HoveredVesselBox(
                    activeHoverVesselState = activeHoverVesselState
                )
            }
        }

        Column(
            modifier = Modifier
                .width(200.dp)
                .fillMaxHeight()
        ) {
            Spacer(Modifier.weight(1f))

            LegendBox(
            )
        }
    }
}
