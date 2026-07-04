package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipCategory
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.style.gap

@Composable
fun RadarPortrait(
    state: ShipermansFriendState,
    sizeFactor: Float,
    selectedShipCategory: ShipCategory?,
    location: Location,
    currentRadarRadius: Double,
    selectedVessel: AisDataUi,
    vessels: List<AisDataUi>,
    safetyDevices: List<AisDataUi>,
    activeHoverVesselState: MutableState<List<AisDataUi>>,
    onAction: (ShipermansFriendAction) -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
    ) {
        RadarBox(
            modifier = Modifier
                .weight(1f),
            state = state,
            sizeFactor,
            location = location,
            currentRadarRadius = currentRadarRadius,
            selectedVessel = selectedVessel,
            vessels = vessels,
            safetyDevices,
            { activeHoverVessels ->
                activeHoverVesselState.value = activeHoverVessels
            },
            activeHoverVesselState,
            onAction
        )

        LegendBox(
            modifier = Modifier
                .fillMaxWidth(),
            selectedShipCategory = selectedShipCategory,
            onAction = onAction
        )
    }
}
