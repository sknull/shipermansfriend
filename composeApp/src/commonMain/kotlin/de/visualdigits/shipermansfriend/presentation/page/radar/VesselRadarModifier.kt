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
import de.visualdigits.shipermansfriend.presentation.style.RadarDisc
import de.visualdigits.shipermansfriend.presentation.style.RadarGrid
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin

fun Modifier.vesselRadar(
    radarHeartbeat: Long,
    pulseRadiusScale: Float,
//    lineAngleScale: Float,
    location: Location,
    currentRadarRadius: Double,
    selectedVessel: AisDataUi,
    vessels: List<AisDataUi>,
    imageSelected: ImageBitmap,
    imageOther: ImageBitmap,
    imageOtherFilled: ImageBitmap
): Modifier {


    return drawWithCache {

        val radius = min(size.width, size.height) / 2.0f - 10.0f
        val drawCenter = Offset(x = size.width / 2.0f, y = size.height / 2.0f)

        onDrawWithContent {
            val ticker = radarHeartbeat // DO NOT REMOVE - NEEDED FOR PERFORMANCE
            val currentPulseRadius = pulseRadiusScale
//            val currentAngle = lineAngleScale

            drawRadarGrid(
                center = drawCenter,
                radius = radius,
//                currentAngle = currentAngle
            )

            // other vessels
            vessels
                .filter { vessel -> vessel.mmsi != selectedVessel.mmsi }
                .forEach { vessel ->
                    drawVessel(
                        vessel = vessel,
                        location = location,
                        radarRadiusPx = radius,
                        maxRadarDistanceMeters = currentRadarRadius,
                        drawCenter = drawCenter,
                        currentPulseRadius = currentPulseRadius,
                        imageSelected = imageSelected,
                        imageOther = imageOther,
                        imageOtherFilled = imageOtherFilled,
                    )
                }

            // selected vessel
            drawVessel(
                vessel = selectedVessel,
                location = location,
                radarRadiusPx = radius,
                maxRadarDistanceMeters = currentRadarRadius,
                drawCenter = drawCenter,
                isSelected = true,
                currentPulseRadius = currentPulseRadius,
                imageSelected = imageSelected,
                imageOther = imageOther,
                imageOtherFilled = imageOtherFilled,
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
    imageOther: ImageBitmap,
    imageOtherFilled: ImageBitmap
) {
    val color = vessel.shipType?.category?.color ?: ShipCategory.Unknown.color

    val offset = location.calculateRadarOffset(
        other = vessel.extrapolatedPosition(),
        radarRadiusPx = radarRadiusPx,
        maxRadarDistanceMeters = maxRadarDistanceMeters,
        center = drawCenter
    )

    if (offset != Offset.Unspecified) {
        val size = vessel.calculateRadarSize(radarRadiusPx, maxRadarDistanceMeters)
        val fraction = currentPulseRadius / 48f

        if (vessel.hasSafetyMessage && vessel.hasCriticalSafetyMessage && !isSelected) {
            drawCircle(
                color = Color.Red.copy(alpha = 1f - fraction),
                style = Fill,
                radius = currentPulseRadius * 1.5f,
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
                if (isSelected) {
                    drawCircle(
                        color = Color.White.copy(alpha = 1f - fraction),
                        style = Fill,
                        radius = 48.0f,
                        center = offset
                    )
                    drawImage(
                        image = imageSelected,
                        dstOffset = IntOffset(x = (offset.x - 48).roundToInt(), y = (offset.y - 48).roundToInt()),
                        dstSize = IntSize(width = 96, height = 96),
                        colorFilter = ColorFilter.tint(
                            color = color,
                            blendMode = BlendMode.SrcIn
                        )
                    )
                } else {
                    drawImage(
                        image = imageOtherFilled,
                        dstOffset = IntOffset(x = (offset.x - 48).roundToInt(), y = (offset.y - 48).roundToInt()),
                        dstSize = IntSize(width = 52, height = 96)
                    )
                    drawImage(
                        image = imageOther,
                        dstOffset = IntOffset(x = (offset.x - 48).roundToInt(), y = (offset.y - 48).roundToInt()),
                        dstSize = IntSize(width = 52, height = 96),
                        colorFilter = ColorFilter.tint(
                            color = color,
                            blendMode = BlendMode.SrcIn
                        )
                    )
                }
            }
        } else {
            if (size == Size.Unspecified) {
                drawCircle(
                    color = color,
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
                        color = color,
                        style = Fill,
                        size = size,
                        topLeft = Offset(x = offset.x - size.width, y = offset.y - size.height)
                    )
                }
            }
        }
    }
}

private fun ContentDrawScope.drawRadarGrid(
    center: Offset,
    radius: Float,
//    currentAngle: Float
) {
    // background disc
    drawCircle(
        color = RadarDisc,
        style = Fill,
        radius = radius,
        center = center
    )

    // radar line
//    var a = 0.0f
//    while (a < 45.0f) {
//        val ar = ((currentAngle - 90.0 - a) * PI / 180.0).toFloat()
//        val alpha = (45.0f - a) / 90.0f
//        val r2 = radius
//        drawLine(
//            color = RadarGrid.copy(alpha = alpha),
//            start = center,
//            end = Offset(center.x + r2 * cos(ar), center.y + r2 * sin(ar)),
//            strokeWidth = 1.0f
//        )
//
//        a += 0.2f
//    }


    drawAngleMarkers(
        center = center, radius = radius, step = 1.0f,
        strokeWidth = 1.0f,
        length = 5.0f,
    )
    drawAngleMarkers(
        center = center, radius = radius, step = 5.0f,
        strokeWidth = 2.0f,
        length = 7.0f,
    )
    drawRings(center, radius)

    // cross for 4 directions
    drawLine(
        color = RadarGrid,
        start = Offset(center.x - radius, center.y),
        end = Offset(center.x + radius, center.y),
        strokeWidth = 1.dp.toPx()
    )
    drawLine(
        color = RadarGrid,
        start = Offset(center.x, center.y - radius),
        end = Offset(center.x, center.y + radius),
        strokeWidth = 1.dp.toPx()
    )
}

private fun ContentDrawScope.drawRings(
    center: Offset,
    radius: Float
) {
    // center dot
    drawCircle(
        color = RadarGrid,
        style = Fill,
        radius = 5.0f,
        center = center
    )

    var r = 0.0f
    val step = radius / 5.0f
    while (r <= radius) {
        drawCircle(
            color = RadarGrid,
            radius = r,
            center = center,
            style = Stroke(width = 1.dp.toPx())
        )

        r += step
    }
}

private fun ContentDrawScope.drawAngleMarkers(
    center: Offset,
    radius: Float,
    step: Float,
    strokeWidth: Float,
    length: Float,
) {
    var a = 0.0f
    val r1 = radius + 3.0f * length
    val r2 = radius + 3.0f
    while (a < 360.0f) {
        val ar = ((a - 90.0) * PI / 180.0).toFloat()

        drawLine(
            color = RadarGrid,
            start = Offset(center.x + r1 * cos(ar), center.y + r1 * sin(ar)),
            end = Offset(center.x + r2 * cos(ar), center.y + r2 * sin(ar)),
            strokeWidth = strokeWidth
        )
        a += step
    }
}
