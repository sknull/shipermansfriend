package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import kotlinx.coroutines.delay

@Composable
fun RadarBox(
    modifier: Modifier = Modifier,
    location: Location,
    currentRadarRadius: Double,
    selectedVessel: AisDataUi,
    vessels: List<AisDataUi>,
    activeHoverNameState: MutableState<String?>,
    setActiveHoverName: (String?) -> Unit,
    imageHeading: ImageBitmap,
    colorBackground: Color,
    colorGrid: Color,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "RadarPulse")
    val pulseRadiusScale by infiniteTransition.animateFloat(
        initialValue = 4f,
        targetValue = 24f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "PulseScale"
    )

    var radarHeartbeat by remember { mutableStateOf(0L) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(200)
            radarHeartbeat++
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .radarHover(
                location = location,
                currentRadarRadius = currentRadarRadius,
                selectedVessel = selectedVessel,
                vessels = vessels,
                activeHoverNameState = activeHoverNameState,
                setActiveHoverName = setActiveHoverName,
            )
            .vesselRadar(
                radarHeartbeat = radarHeartbeat,
                pulseRadiusScale = pulseRadiusScale,
                location = location,
                currentRadarRadius = currentRadarRadius,
                selectedVessel = selectedVessel,
                vessels = vessels,
                imageHeading = imageHeading,
                colorBackground = colorBackground,
                colorGrid = colorGrid
            )
    )
}
