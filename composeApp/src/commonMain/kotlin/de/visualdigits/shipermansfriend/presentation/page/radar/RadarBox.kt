package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.image_direction_96px
import de.visualdigits.compose.resources.image_navigation_96px
import de.visualdigits.compose.resources.image_navigation_filled_96px
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipCategory
import de.visualdigits.shipermansfriend.domain.model.settings.SK
import de.visualdigits.shipermansfriend.domain.model.type.CategoryMode
import de.visualdigits.shipermansfriend.domain.util.parseDistance
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.style.RadarGrid
import de.visualdigits.shipermansfriend.presentation.style.gap
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.imageResource
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun RadarBox(
    modifier: Modifier = Modifier,
    state: ShipermansFriendState,
    sizeFactor: Float,
    location: Location,
    currentRadarRadius: Double,
    selectedVessel: AisDataUi?,
    selectedShipCategories: Map<ShipCategory, CategoryMode>,
    vessels: List<AisDataUi>,
    safetyDevices: List<AisDataUi>,
    setActiveHoverName: (List<AisDataUi>) -> Unit,
    activeHoverVesselState: MutableState<List<AisDataUi>>,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val radiusInner = state.settings?.get<String>(SK.radiusInner)?.parseDistance() ?: 1000.0

    val imageSelected = imageResource(Res.drawable.image_direction_96px)
    val imageOther = imageResource(Res.drawable.image_navigation_96px)
    val imageOtherFilled = imageResource(Res.drawable.image_navigation_filled_96px)

    val radarPulseTransition = rememberInfiniteTransition(label = "RadarPulse")
    val pulseRadiusScale by radarPulseTransition.animateFloat(
        initialValue = 0.0f,
        targetValue = 12.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "PulseScale"
    )

    val currentTime = KmpOffsetDateTime.now()

//    val radarLineTransition = rememberInfiniteTransition(label = "RadarLine")
//    val lineAngleScale by radarLineTransition.animateFloat(
//        initialValue = 0.0f,
//        targetValue = 360.0f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(5000, easing = LinearEasing),
//            repeatMode = RepeatMode.Restart
//        ),
//        label = "AngleScale"
//    )

    var radarHeartbeat by remember { mutableLongStateOf(0L) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(40.milliseconds) // 25 fps
            radarHeartbeat++
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, RadarGrid)
                .padding(MaterialTheme.shapes.gap)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp - MaterialTheme.shapes.gap)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .radarHover(
                            location = location,
                            currentTime = currentTime,
                            currentRadarRadius = currentRadarRadius,
                            vessels = vessels,
                            setActiveHoverVessel = setActiveHoverName,
                        )
                        .vesselRadar(
                            radarHeartbeat = radarHeartbeat,
                            pulseRadiusScale = pulseRadiusScale,
//                        lineAngleScale = lineAngleScale,
                            location = location,
                            currentTime = currentTime,
                            currentRadarRadius = currentRadarRadius,
                            selectedVessel = selectedVessel,
                            vessels = vessels,
                            imageSelected = imageSelected,
                            imageOther = imageOther,
                            imageOtherFilled = imageOtherFilled
                        )
                )
            }

            RadarOverlay(
                state = state,
                sizeFactor = sizeFactor,
                currentRadarRadius = currentRadarRadius,
                setCurrentRadarRadius = { radius ->
                    onAction(ShipermansFriendAction.OnRadarRadiusChange(radius))
                },
                radiusInner = radiusInner,
                selectedShipCategories = selectedShipCategories,
                selectedVessel = selectedVessel,
                vesselNumber = vessels.size,
                safetyDeviceNumber = safetyDevices.size,
                onAction = onAction
            )

            VesselHoverBox(
                activeHoverVesselState = activeHoverVesselState
            )
        }
    }
}
