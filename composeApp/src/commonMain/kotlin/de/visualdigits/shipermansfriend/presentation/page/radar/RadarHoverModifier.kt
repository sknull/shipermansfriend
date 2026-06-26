package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import kotlin.math.pow
import kotlin.math.sqrt

fun Modifier.radarHover(
    location: Location,
    currentRadarRadius: Double,
    selectedVessel: AisDataUi,
    vessels: List<AisDataUi>,
    activeHoverNameState: MutableState<String?>,
    setActiveHoverName: (String?) -> Unit
): Modifier {
    return pointerInput(vessels, location, currentRadarRadius) {
        // pointerInput lauscht auf Mausbewegungen auf dem Desktop (und Touch auf Mobile)
        awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent()
                val type = event.type

                when (type) {
                    PointerEventType.Move, PointerEventType.Press -> {
                        event.changes.forEach { it.consume() }

                        val position = event.changes.first().position
                        val canvasRadius = StrictMath.min(size.width, size.height) / 2.0f
                        val drawCenter = Offset(size.width / 2.0f, size.height / 2.0f)

                        var foundVessel: String? = null

                        val currentBoundingBox = location.calculateBoundingBox(currentRadarRadius)
                        vessels
                            .filter { vessel -> vessel.mmsi != selectedVessel.mmsi && vessel.location.isInBoundingBox(currentBoundingBox) }
                            .forEach { vessel ->
                                val vesselLoc = vessel.extrapolatedPosition
                                val vesselOffset = location.calculateRadarOffset(
                                    other = vesselLoc,
                                    radarRadiusPx = canvasRadius,
                                    maxRadarDistanceMeters = currentRadarRadius,
                                    center = drawCenter
                                )

                                val distanceToMouse = sqrt(
                                    (position.x - vesselOffset.x).pow(2) + (position.y - vesselOffset.y).pow(2)
                                )

                                if (distanceToMouse <= 15f) {
                                    foundVessel = vessel.name
                                }
                            }

                        if (activeHoverNameState.value != foundVessel) {
                            setActiveHoverName(foundVessel)
                        }
                    }

                    PointerEventType.Exit, PointerEventType.Release -> {
                        setActiveHoverName(null)
                    }
                }
            }
        }
    }
}
