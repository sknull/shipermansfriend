package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipCategory
import kotlin.math.roundToInt

fun Modifier.vesselRadar(
    radarHeartbeat: Long,
    pulseRadiusScale: Float,
    location: Location,
    currentRadarRadius: Double,
    selectedVessel: AisDataUi,
    vessels: List<AisDataUi>,
    imageHeading: ImageBitmap,
    colorBackground: Color,
    colorGrid: Color
): Modifier {
    return drawWithCache {

        val radius = StrictMath.min(size.width, size.height) / 2.0f
        val drawCenter = Offset(x = size.width / 2.0f, y = size.height / 2.0f)

        onDrawWithContent {
            val ticker = radarHeartbeat // DO NOT REMOVE - NEEDED FOR PERFORMANCE
            val currentPulseRadius = pulseRadiusScale

            drawRadarGrid(
                center = drawCenter,
                radius = radius,
                colorBackground = colorBackground,
                colorGrid = colorGrid
            )

            // other vessels
            val currentBoundingBox = location.calculateBoundingBox(currentRadarRadius)
            vessels
                .filter { vessel -> vessel.mmsi != selectedVessel.mmsi && vessel.location.isInBoundingBox(currentBoundingBox) }
                .forEach { vessel ->

                    drawVessel(
                        vessel = vessel,
                        location = location,
                        currentPulseRadius = currentPulseRadius,
                        radarRadiusPx = radius,
                        maxRadarDistanceMeters = currentRadarRadius,
                        drawCenter = drawCenter,
                        imageSelected = imageHeading,
                        imageOther = imageHeading,
                    )
                }

            // selected vessel
            drawVessel(
                vessel = selectedVessel,
                location = location,
                isSelected = true,
                currentPulseRadius = currentPulseRadius,
                radarRadiusPx = radius,
                maxRadarDistanceMeters = currentRadarRadius,
                drawCenter = drawCenter,
                imageSelected = imageHeading,
                imageOther = imageHeading,
            )
        }
    }
}

private fun ContentDrawScope.drawVessel(
    vessel: AisDataUi,
    location: Location,
    radarRadiusPx: Float,
    maxRadarDistanceMeters: Double,
    drawCenter: Offset,
    isSelected: Boolean = false,
    currentPulseRadius: Float,
    imageSelected: ImageBitmap,
    imageOther: ImageBitmap
) {
    val image = if (isSelected) imageSelected else imageOther
    val color = vessel.shipType?.category?.color ?: ShipCategory.Unknown.color

    val offset = location.calculateRadarOffset(
        other = vessel.extrapolatedPosition(),
        radarRadiusPx = radarRadiusPx,
        maxRadarDistanceMeters = maxRadarDistanceMeters,
        center = drawCenter
    )
    val size = vessel.calculateRadarSize(radarRadiusPx, maxRadarDistanceMeters)

    if (isSelected) {
        // pulsing circle
        val fraction = currentPulseRadius / 24f
        drawCircle(
            color = Color.White.copy(alpha = 1f - fraction), // Wird nach außen transparenter
            style = Fill,
            radius = currentPulseRadius,
            center = offset
        )
    }

    if (vessel.sog > 0.5) {
        withTransform({
            scale(
                scaleX = 0.25f,
                scaleY = 0.25f,
                pivot = offset
            )
            rotate(
                degrees = vessel.heading.toFloat(),
                pivot = offset
            )
        }) {
            drawImage(
                image = image,
                dstOffset = IntOffset(x = (offset.x - 48).roundToInt(), y = (offset.y - 48).roundToInt()),
                dstSize = IntSize(width = 96, height = 96),
                colorFilter = ColorFilter.tint(
                    color = color,
                    blendMode = BlendMode.SrcIn
                )
            )
        }
    } else {
        if (size == Size.Unspecified) {
            drawCircle(
                color = if (isSelected) color else color.copy(alpha = 0.5f),
                style = Fill,
                radius = 5.0f,
                center = offset
            )
        } else {
            withTransform({
                rotate(
                    degrees = vessel.heading.toFloat(),
                    pivot = offset
                )
            }) {
                drawRect(
                    color = if (isSelected) color else color.copy(alpha = 0.5f),
                    style = Fill,
                    size = size,
                    topLeft = Offset(x = offset.x - size.width, y = offset.y - size.height)
                )
            }
        }
    }
}

private fun ContentDrawScope.drawRadarGrid(
    center: Offset,
    radius: Float,
    colorBackground: Color,
    colorGrid: Color
) {
    // background disc
    drawCircle(
        color = colorBackground,
        style = Fill,
        radius = radius,
        center = center
    )

    // 3 distance circles
    drawCircle(
        color = colorGrid,
        radius = radius,
        center = center,
        style = Stroke(width = 3.dp.toPx())
    )
    drawCircle(
        color = colorGrid,
        radius = radius * 0.66f,
        center = center,
        style = Stroke(width = 1.dp.toPx())
    )
    drawCircle(
        color = colorGrid,
        radius = radius * 0.33f,
        center = center,
        style = Stroke(width = 1.dp.toPx())
    )

    // cross for 4 directions
    drawLine(
        color = colorGrid,
        start = Offset(center.x - radius, center.y),
        end = Offset(center.x + radius, center.y),
        strokeWidth = 1.dp.toPx()
    )
    drawLine(
        color = colorGrid,
        start = Offset(center.x, center.y - radius),
        end = Offset(center.x, center.y + radius),
        strokeWidth = 1.dp.toPx()
    )

    // center dot
    drawCircle(
        color = Color(0xFF00FF00),
        style = Fill,
        radius = 5.0f,
        center = center
    )
}
