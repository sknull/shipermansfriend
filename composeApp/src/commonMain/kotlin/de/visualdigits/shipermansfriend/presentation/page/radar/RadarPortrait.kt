package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipCategory
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.page.search.VesselSearchBar
import de.visualdigits.shipermansfriend.presentation.style.gap

@Composable
fun RadarPortrait(
    state: ShipermansFriendState,
    selectedShipCategory: ShipCategory?,
    location: Location,
    currentRadarRadius: Double,
    selectedVessel: AisDataUi,
    vessels: List<AisDataUi>,
    activeHoverVesselState: MutableState<List<AisDataUi>>,
    imageHeading: ImageBitmap,
    colorBackground: Color,
    colorGrid: Color,
    onAction: (ShipermansFriendAction) -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
    ) {
        VesselSearchBar(
            state = state,
            onAction = onAction
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            RadarBox(
                location = location,
                currentRadarRadius = currentRadarRadius,
                selectedVessel = selectedVessel,
                vessels = vessels,
                setActiveHoverName = { activeHoverVessels ->
                    activeHoverVesselState.value = activeHoverVessels
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
            selectedShipCategory = selectedShipCategory,
            onAction = onAction
        )
    }
}
