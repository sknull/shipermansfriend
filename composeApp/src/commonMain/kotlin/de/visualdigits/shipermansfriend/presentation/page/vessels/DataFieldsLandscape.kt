package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.presentation.components.button.IndicatorButton
import de.visualdigits.common.presentation.components.util.conditional
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_direction_24px
import de.visualdigits.compose.resources.icon_my_location_24px
import de.visualdigits.compose.resources.icon_warning_24px
import de.visualdigits.compose.resources.label_callsign
import de.visualdigits.compose.resources.label_destination
import de.visualdigits.compose.resources.label_knots
import de.visualdigits.compose.resources.label_last_message
import de.visualdigits.compose.resources.label_length
import de.visualdigits.compose.resources.label_maxDraught
import de.visualdigits.compose.resources.label_minutes
import de.visualdigits.compose.resources.label_moored
import de.visualdigits.compose.resources.label_width
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueEvenLighter
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueLight
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueLighter
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import de.visualdigits.shipermansfriend.presentation.style.gap
import de.visualdigits.shipermansfriend.presentation.util.routePlatformLink
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun DataFieldsLandscape(
    cellWidth: Dp,
    vessel: AisDataUi
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val cellHeight = 40.dp
    val labelWidth = cellWidth * 2 / 5
    val valueWidth = cellWidth * 3 / 5

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(cellHeight + 10.dp),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(MarineBlueEvenLighter)
                    .width(cellWidth)
                    .height(cellHeight + 10.dp)
                    .padding(MaterialTheme.shapes.gap),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = vessel.distanceString,
                    style = MaterialTheme.typography.labelLarge
                )
                Icon(
                    modifier = Modifier
                        .height(30.dp)
                        .rotate(vessel.heading.toFloat()),
                    painter = painterResource(Res.drawable.icon_direction_24px),
                    contentDescription = null,
                    tint = TextColor
                )
                Column(
                ) {
                    Text(
                        text = if (vessel.sog > 0.5) "${vessel.sog} ${stringResource(Res.string.label_knots)}" else stringResource(Res.string.label_moored),
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = if (vessel.sog > 0.5) vessel.speedKmh else "",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Row(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(MarineBlueLighter)
                    .width(cellWidth)
                    .height(cellHeight + 10.dp)
                    .padding(MaterialTheme.shapes.gap),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .width(labelWidth),
                    text = stringResource(Res.string.label_last_message),
                    style = MaterialTheme.typography.labelSmall,
                )
                Text(
                    modifier = Modifier
                        .width(valueWidth),
                    text = "${KmpOffsetDateTime.now().minus(vessel.timeUtc).inWholeMinutes} ${stringResource(Res.string.label_minutes)}",
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            val enabledDestination = vessel.destination?.isNotBlank() == true
            Row(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(MarineBlueLighter)
                    .width(cellWidth)
                    .height(cellHeight + 10.dp)
                    .padding(MaterialTheme.shapes.gap),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .width(labelWidth),
                    text = stringResource(Res.string.label_destination),
                    style = MaterialTheme.typography.labelSmall,
                )
                Text(
                    modifier = Modifier
                        .width(valueWidth),
                    text = if (enabledDestination) vessel.destination else "?",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(cellHeight),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val normalizedMmsi = vessel.mmsi.toString().padStart(9, '0')
            IndicatorButton(
                buttonColor = MarineBlueLight,
                textColor = Color.White,
                horizontalArrangement = Arrangement.Start,
                width = cellWidth,
                height = cellHeight,
                onClick = {
                    routePlatformLink("https://www.startpage.com/do/dsearch?query=mmsi%20$normalizedMmsi")
                },
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.shapes.gap / 2),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .width(labelWidth),
                            text = "MMSI",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )

                        Text(
                            modifier = Modifier
                                .width(valueWidth),
                            text = vessel.mmsi.toString(),
                            textAlign = TextAlign.Start,
                            style = if (isHovered) MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.Underline) else MaterialTheme.typography.bodySmall,
                            color = Color.White
                        )
                    }
                }
            )

            val enabledImo = vessel.imoNumber?.equals(0L) == false
            IndicatorButton(
                buttonColor = MarineBlueLight,
                textColor = if (enabledImo) Color.White else Color.Gray,
                horizontalArrangement = Arrangement.Start,
                width = cellWidth,
                height = cellHeight,
                enabled = enabledImo,
                onClick = {
                    routePlatformLink("https://www.startpage.com/do/dsearch?query=imo%20${vessel.imoNumber}")
                },
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.shapes.gap / 2),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .width(labelWidth),
                            text = "IMO",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (enabledImo) Color.White else Color.Gray,
                        )

                        Text(
                            modifier = Modifier
                                .width(valueWidth),
                            text = if (enabledImo) vessel.imoNumber.toString() else "?",
                            textAlign = TextAlign.Start,
                            style = if (isHovered) MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.Underline) else MaterialTheme.typography.bodySmall,
                            color = if (enabledImo) Color.White else Color.Gray,
                        )
                    }
                }
            )

            val enabledCallsign = vessel.callSign?.isNotBlank() == true
            IndicatorButton(
                buttonColor = MarineBlueLight,
                textColor = if (enabledCallsign) Color.White else Color.Gray,
                horizontalArrangement = Arrangement.Start,
                width = cellWidth,
                height = cellHeight,
                enabled = enabledCallsign,
                onClick = {
                    routePlatformLink("https://www.startpage.com/do/dsearch?query=callsign%20${vessel.callSign}")
                },
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.shapes.gap / 2),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .width(labelWidth),
                            text = stringResource(Res.string.label_callsign),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (enabledCallsign) Color.White else Color.Gray,
                        )

                        Text(
                            modifier = Modifier
                                .width(valueWidth),
                            text = if(enabledCallsign) vessel.callSign else "?",
                            textAlign = TextAlign.Start,
                            style = if (isHovered) MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.Underline) else MaterialTheme.typography.bodySmall,
                            color = if (enabledCallsign) Color.White else Color.Gray,
                        )
                    }
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(MarineBlueLighter)
                    .width(cellWidth)
                    .height(cellHeight)
                    .padding(MaterialTheme.shapes.gap),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .width(labelWidth),
                    text = stringResource(Res.string.label_maxDraught),
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    modifier = Modifier
                        .width(valueWidth),
                    text = vessel.maximumStaticDraught?.let { l -> "$l m" } ?: "?",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Row(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(MarineBlueLighter)
                    .width(cellWidth)
                    .height(cellHeight)
                    .padding(MaterialTheme.shapes.gap),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .width(labelWidth),
                    text = stringResource(Res.string.label_length),
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    modifier = Modifier
                        .width(valueWidth),
                    text = vessel.totalLength?.let { l -> "$l m" } ?: "?",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Row(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(MarineBlueLighter)
                    .width(cellWidth)
                    .height(cellHeight)
                    .padding(MaterialTheme.shapes.gap),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .width(labelWidth),
                    text = stringResource(Res.string.label_width),
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    modifier = Modifier
                        .width(valueWidth),
                    text = vessel.totalWidth?.let { w -> "$w m" } ?: "?",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        if (vessel.hasSafetyMessage) {
            val isCriticalMessage = vessel.hasSafetyMessage && vessel.hasCriticalSafetyMessage
            Row(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraSmall)
                    .conditional(isCriticalMessage) { background(Color.Red) }
                    .conditional(!isCriticalMessage) { background(MarineBlueLighter) }
                    .fillMaxWidth()
                    .height(cellHeight)
                    .padding(MaterialTheme.shapes.gap),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .height(cellHeight - 5.dp),
                    painter = painterResource(Res.drawable.icon_warning_24px),
                    contentDescription = null,
                    tint = if (isCriticalMessage) Color.White else TextColor
                )
                Text(
                    text = vessel.text?:"?",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isCriticalMessage) Color.White else TextColor
                )
            }

            Row(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraSmall)
                    .conditional(isCriticalMessage) { background(Color.Red) }
                    .conditional(!isCriticalMessage) { background(MarineBlueLighter) }
                    .fillMaxWidth()
                    .height(cellHeight)
                    .padding(MaterialTheme.shapes.gap),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .height(cellHeight - 5.dp),
                    painter = painterResource(Res.drawable.icon_my_location_24px),
                    contentDescription = null,
                    tint = if (isCriticalMessage) Color.White else TextColor
                )
                Text(
                    text = vessel.location.toDmsString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isCriticalMessage) Color.White else TextColor
                )
            }
        }
    }
}
