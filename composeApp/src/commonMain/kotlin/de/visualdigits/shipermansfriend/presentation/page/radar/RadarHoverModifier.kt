package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import kotlin.math.pow
import kotlin.math.sqrt

fun Modifier.radarHover(
    location: Location,
    currentTime: KmpOffsetDateTime,
    currentRadarRadius: Double,
    vessels: List<AisDataUi>,
    setActiveHoverVessel: (List<AisDataUi>) -> Unit
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

                        val foundVessels = findVesselsUnderPointer(
                            vessels = vessels,
                            location = location,
                            currentTime = currentTime,
                            canvasRadius = canvasRadius,
                            currentRadarRadius = currentRadarRadius,
                            drawCenter = drawCenter,
                            position = position
                        )

                        setActiveHoverVessel(foundVessels)
                    }

                    PointerEventType.Exit, PointerEventType.Release -> {
                        setActiveHoverVessel(listOf())
                    }
                }
            }
        }
    }
}

private fun findVesselsUnderPointer(
    vessels: List<AisDataUi>,
    location: Location,
    currentTime: KmpOffsetDateTime,
    canvasRadius: Float,
    currentRadarRadius: Double,
    drawCenter: Offset,
    position: Offset
): List<AisDataUi> = vessels
    .filter { vessel ->
        val vesselLoc = vessel.extrapolatedPosition(currentTime)
        val vesselOffset = location.calculateRadarOffset(
            other = vesselLoc,
            radarRadiusPx = canvasRadius,
            maxRadarDistanceMeters = currentRadarRadius,
            center = drawCenter
        )

        if (vesselOffset != Offset.Unspecified) {
            val distanceToMouse = sqrt(
                (position.x - vesselOffset.x).pow(2) + (position.y - vesselOffset.y).pow(2)
            )

            distanceToMouse <= 15f
        } else {
            false
        }
    }
