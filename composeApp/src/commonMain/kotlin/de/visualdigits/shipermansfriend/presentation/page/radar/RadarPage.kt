package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.page.search.VesselSearchBar
import de.visualdigits.shipermansfriend.presentation.style.RadarBackground
import de.visualdigits.shipermansfriend.presentation.style.RadarGrid

@Composable
fun RadarPage(
    viewModel: ShipermansFriendViewModel,
    state: ShipermansFriendState,
    sizeFactor: Float,
    location: Location,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val selectedVessel = state.selectedVessel
    val activeHoverVesselState = remember { mutableStateOf<List<AisDataUi>>(emptyList()) }

    val vessels by viewModel.vesselsOnRadar.collectAsStateWithLifecycle()
    val currentTime = KmpOffsetDateTime.now()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RadarBackground)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        VesselSearchBar(
            modifier = Modifier
                .height(30.dp)
                .padding(0.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
            shape = RectangleShape,
            textColor = Color.White,
            placeholderColor = RadarGrid,
            iconTint = RadarGrid,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = RadarGrid,
                unfocusedBorderColor = RadarGrid
            ),
            state = state,
            onAction = onAction
        )

        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            RadarBox(
                modifier = Modifier
                    .weight(1f),
                viewModel = viewModel,
                state = state,
                sizeFactor = sizeFactor,
                location = location,
                currentTime = currentTime,
                currentRadarRadius = state.currentRadarRadius,
                selectedVessel = selectedVessel,
                vessels = vessels,
                setActiveHoverName = { activeHoverName ->
                    activeHoverVesselState.value = activeHoverName
                },
                activeHoverVesselState = activeHoverVesselState,
                onAction = onAction
            )
        }
    }
}
