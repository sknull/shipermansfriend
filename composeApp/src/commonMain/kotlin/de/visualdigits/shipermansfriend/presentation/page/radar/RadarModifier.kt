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
import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.util.color
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.style.RadarDisc
import de.visualdigits.shipermansfriend.presentation.style.RadarGrid
import de.visualdigits.shipermansfriend.presentation.style.RadarLine
import de.visualdigits.shipermansfriend.presentation.style.RedAlert
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
    currentTime: KmpOffsetDateTime,
    radiusInner: Double,
    currentRadarRadius: Double,
    selectedVessel: AisDataUi?,
    vessels: List<AisDataUi>,
    vesselsAlerted: List<Long>,
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

            val radiusInnerPx = (radius / currentRadarRadius * radiusInner).toFloat()

            drawRadarGrid(
                radiusInnerPx = radiusInnerPx,
                center = drawCenter,
                radiusPx = radius,
                color = RadarGrid
//                currentAngle = currentAngle
            )

            // other vessels without safety message
            vessels
                .filter { vessel -> vessel.mmsi != selectedVessel?.mmsi && !vessel.hasSafetyMessage }
                .forEach { vessel ->
                    drawVessel(
                        vessel = vessel,
                        location = location,
                        currentTime = currentTime,
                        radarRadiusPx = radius,
                        maxRadarDistanceMeters = currentRadarRadius,
                        drawCenter = drawCenter,
                        currentPulseRadius = currentPulseRadius,
                        imageSelected = imageSelected,
                        imageOther = imageOther,
                        imageOtherFilled = imageOtherFilled,
                        hasAlert = vesselsAlerted.contains(vessel.mmsi),
                    )
                }

            // draw vessels with safety message on top of the others
            vessels
                .filter { vessel -> vessel.mmsi != selectedVessel?.mmsi && vessel.hasSafetyMessage }
                .forEach { vessel ->
                    drawVessel(
                        vessel = vessel,
                        location = location,
                        currentTime = currentTime,
                        radarRadiusPx = radius,
                        maxRadarDistanceMeters = currentRadarRadius,
                        drawCenter = drawCenter,
                        currentPulseRadius = currentPulseRadius,
                        imageSelected = imageSelected,
                        imageOther = imageOther,
                        imageOtherFilled = imageOtherFilled,
                        hasAlert = vesselsAlerted.contains(vessel.mmsi),
                    )
                }

            // selected vessel
            selectedVessel?.also { sv ->
                drawVessel(
                    vessel = sv,
                    location = location,
                    currentTime = currentTime,
                    radarRadiusPx = radius,
                    maxRadarDistanceMeters = currentRadarRadius,
                    drawCenter = drawCenter,
                    currentPulseRadius = currentPulseRadius,
                    imageSelected = imageSelected,
                    imageOther = imageOther,
                    imageOtherFilled = imageOtherFilled,
                    hasAlert = vesselsAlerted.contains(sv.mmsi),
                    isSelected = true,
                )
            }
        }
    }
}

private fun ContentDrawScope.drawVessel(
    vessel: AisDataUi,
    location: Location,
    currentTime: KmpOffsetDateTime,
    radarRadiusPx: Float,
    maxRadarDistanceMeters: Double,
    drawCenter: Offset,
    isSelected: Boolean = false,
    hasAlert: Boolean,
    currentPulseRadius: Float,
    imageSelected: ImageBitmap,
    imageOther: ImageBitmap,
    imageOtherFilled: ImageBitmap
) {
    val color = vessel.shipType.category.color

    val offset = location.calculateRadarOffset(
        other = vessel.extrapolatedPosition(currentTime),
        radarRadiusPx = radarRadiusPx,
        maxRadarDistanceMeters = maxRadarDistanceMeters,
        center = drawCenter
    )

    if (offset != Offset.Unspecified) {
        val size = vessel.calculateRadarSize(radarRadiusPx, maxRadarDistanceMeters)
        val fraction = currentPulseRadius / 12.0f

        // ensure we highlight the selected vessel even if it has a severe message
        if (vessel.messageSeverity > Severity.Info && !isSelected) {
            drawCircle(
                color = vessel.messageSeverity.color().copy(alpha = 1f - fraction),
                style = Fill,
                radius = 20.0f,
                center = offset
            )
        } else if (isSelected) {
            drawCircle(
                color = Color.White.copy(alpha = 1f - fraction),
                style = Fill,
                radius = 12.0f,
                center = offset
            )
        }

        if (hasAlert) {
            drawCircle(
                color = RedAlert,
                style = Stroke(width = 2.0f),
                radius = currentPulseRadius * 1.5f,
                center = offset
            )
        }

        withTransform({
            rotate(
//                degrees = vessel.extrapolateHeading(currentTime).toFloat(),
                degrees = vessel.heading.toFloat(),
                pivot = offset
            )
        }) {
            if (!vessel.isMoored) {
                // rate of turn marker
                if (vessel.rateOfTurnDegreesPerMinute != 0.0) {
                    drawArc(
                        color = if (vessel.rateOfTurnDegreesPerMinute < 0.0) Color.Red else Color.Green,
                        startAngle = -90.0f,
                        sweepAngle = vessel.rateOfTurnDegreesPerMinute.toFloat() * 3.0f, // exaggerate a bit
                        useCenter = true,
                        topLeft = Offset(offset.x - 12.0f, offset.y - 12.0f),
                        size = Size(width = 24.0f, height = 24.0f),
                        alpha = 0.5f
                    )
                }

                if (isSelected) {
                    drawImage(
                        image = imageSelected,
                        dstOffset = IntOffset(x = (offset.x - 12).roundToInt(), y = (offset.y - 12).roundToInt()),
                        dstSize = IntSize(width = 24, height = 24),
                        colorFilter = ColorFilter.tint(
                            color = color,
                            blendMode = BlendMode.SrcIn
                        )
                    )
                } else {
                    drawImage(
                        image = imageOtherFilled,
                        dstOffset = IntOffset(x = (offset.x - 6.5).roundToInt(), y = (offset.y - 12).roundToInt()),
                        dstSize = IntSize(width = 13, height = 24)
                    )
                    drawImage(
                        image = imageOther,
                        dstOffset = IntOffset(x = (offset.x - 6.5).roundToInt(), y = (offset.y - 12).roundToInt()),
                        dstSize = IntSize(width = 13, height = 24),
                        colorFilter = ColorFilter.tint(
                            color = color,
                            blendMode = BlendMode.SrcIn
                        )
                    )
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
                    drawRect(
                        color = color,
                        style = Fill,
                        size = size,
                        topLeft = Offset(x = offset.x - size.width / 2.0f, y = offset.y - size.height / 2.0f)
                    )
                }
            }
        }
    }
}

private fun ContentDrawScope.drawRadarGrid(
    radiusInnerPx: Float,
    center: Offset,
    radiusPx: Float,
    color: Color
//    currentAngle: Float
) {
    // background disc
    drawCircle(
        color = RadarDisc,
        style = Fill,
        radius = radiusPx,
        center = center
    )

    // perimeter
    if (radiusInnerPx < radiusPx) {
        drawCircle(
            color = Color.Yellow,
            style = Stroke(2.0f),
            radius = radiusInnerPx,
            center = center
        )
    }

//    drawRadarLine(currentAngle, center, radius)

    drawAngleMarkers(
        center = center, radius = radiusPx, color = color,
        step = 1.0f,
        strokeWidth = 1.0f,
        length = 5.0f,
    )
    drawAngleMarkers(
        center = center, radius = radiusPx, color = color,
        step = 5.0f,
        strokeWidth = 2.0f,
        length = 7.0f,
    )
    drawRings(center, radiusPx, color)

    // cross for 4 directions
    drawLine(
        color = color,
        start = Offset(center.x - radiusPx, center.y),
        end = Offset(center.x + radiusPx, center.y),
        strokeWidth = 1.dp.toPx()
    )
    drawLine(
        color = color,
        start = Offset(center.x, center.y - radiusPx),
        end = Offset(center.x, center.y + radiusPx),
        strokeWidth = 1.dp.toPx()
    )
}

private fun ContentDrawScope.drawRadarLine(
    currentAngle: Float,
    center: Offset,
    radius: Float
) {
    var a = 0.0f
    while (a < 45.0f) {
        val ar = ((currentAngle - 90.0 - a) * PI / 180.0).toFloat()
        drawLine(
            color = if (45.0f - a > 44.8) RadarLine.copy(alpha = (45.0f - a) / 45.0f) else RadarGrid.copy(alpha = (45.0f - a) / 180.0f),
            start = center,
            end = Offset(center.x + radius * cos(ar), center.y + radius * sin(ar)),
            strokeWidth = 1.0f
        )

        a += 0.2f
    }
}

private fun ContentDrawScope.drawRings(
    center: Offset,
    radius: Float,
    color: Color
) {
    // center dot
    drawCircle(
        color = color,
        style = Fill,
        radius = 5.0f,
        center = center
    )

    var r = 0.0f
    val step = radius / 5.0f
    while (r <= radius) {
        drawCircle(
            color = color,
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
    color: Color,
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
            color = color,
            start = Offset(center.x + r1 * cos(ar), center.y + r1 * sin(ar)),
            end = Offset(center.x + r2 * cos(ar), center.y + r2 * sin(ar)),
            strokeWidth = strokeWidth
        )
        a += step
    }
}
