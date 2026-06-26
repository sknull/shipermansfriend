package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.image_direction_white
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.settings.SK
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

    val radiusInner = state.settings?.get<String>(SK.radiusInner)?.toDouble()?:1000.0
    var currentRadarRadius by remember { mutableStateOf(radiusInner) }

    val selectedVessel = state.selectedVessel!!
    val activeHoverNameState = remember { mutableStateOf<String?>(null) }

    val vessels by viewModel.uiVessels.collectAsStateWithLifecycle()

    val imageHeading = imageResource(Res.drawable.image_direction_white)
    val colorBackground = Color(0xFF004711)
    val colorGrid = Color(0xFF00FF00)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
    ) {
        RadarPageMenuBar(
            currentRadarRadius = currentRadarRadius,
            setCurrentRadarRadius = { radius -> currentRadarRadius = radius },
            radiusInner = radiusInner,
            selectedVessel = selectedVessel,
            onAction = onAction
        )

        if (isLandscape) {
            RadarLandscape(
                location,
                currentRadarRadius,
                selectedVessel,
                vessels,
                activeHoverNameState,
                imageHeading,
                colorBackground,
                colorGrid
            )
        } else {
            RadarPortrait(
                location,
                currentRadarRadius,
                selectedVessel,
                vessels,
                activeHoverNameState,
                imageHeading,
                colorBackground,
                colorGrid
            )
        }
    }
}
