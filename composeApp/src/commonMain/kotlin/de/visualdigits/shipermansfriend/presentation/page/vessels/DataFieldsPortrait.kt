package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
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
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueLight
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueLighter
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import de.visualdigits.shipermansfriend.presentation.style.gap
import de.visualdigits.shipermansfriend.presentation.util.routePlatformLink
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun DataFieldsPortrait(
    vessel: AisDataUi
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val cellHeight = 30.dp

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        val labelWidth = maxWidth / 3
        val valueWidth = maxWidth * 2 / 3

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
        ) {
            Row(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(MarineBlueLighter)
                    .fillMaxWidth()
                    .height(cellHeight + 10.dp)
                    .padding(MaterialTheme.shapes.gap),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
            ) {
                Text(
                    text = vessel.distanceString,
                    style = MaterialTheme.typography.labelMedium
                )
                Icon(
                    modifier = Modifier
                        .height(24.dp)
                        .rotate(vessel.heading.toFloat()),
                    painter = painterResource(Res.drawable.icon_direction_24px),
                    contentDescription = null,
                    tint = TextColor
                )
                Column(
                ) {
                    Text(
                        text = if (!vessel.isMoored) "${vessel.sog} ${stringResource(Res.string.label_knots)}" else stringResource(Res.string.label_moored),
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = if (!vessel.isMoored) vessel.speedKmh else "",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Row(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(MarineBlueLighter)
                    .fillMaxWidth()
                    .height(cellHeight)
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

            Row(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(MarineBlueLighter)
                    .fillMaxWidth()
                    .height(cellHeight)
                    .padding(MaterialTheme.shapes.gap),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
            ) {
                Text(
                    modifier = Modifier
                        .width(labelWidth),
                    text = stringResource(Res.string.label_destination),
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    modifier = Modifier
                        .width(valueWidth),
                    text = if (vessel.destination?.isNotBlank() == true) vessel.destination else "?",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            val normalizedMmsi = vessel.mmsi.toString().padStart(9, '0')
            IndicatorButton(
                modifier = Modifier
                    .fillMaxWidth(),
                buttonColor = MarineBlueLight,
                textColor = Color.White,
                horizontalArrangement = Arrangement.Start,
                width = Dp.Unspecified,
                height = cellHeight,
                onClick = {
                    routePlatformLink("https://www.startpage.com/do/dsearch?query=mmsi%20$normalizedMmsi")
                },
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
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
            if (enabledImo) {
                IndicatorButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    buttonColor = MarineBlueLight,
                    textColor = if (enabledImo) Color.White else Color.Gray,
                    horizontalArrangement = Arrangement.Start,
                    width = Dp.Unspecified,
                    height = cellHeight,
                    enabled = enabledImo,
                    onClick = {
                        routePlatformLink("https://www.startpage.com/do/dsearch?query=imo%20${vessel.imoNumber}")
                    },
                    content = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
                        ) {
                            Text(
                                modifier = Modifier
                                    .width(labelWidth),
                                text = "IMO",
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                            )

                            Text(
                                modifier = Modifier
                                    .width(valueWidth),
                                text = vessel.imoNumber.toString(),
                                textAlign = TextAlign.Start,
                                style = if (isHovered) MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.Underline) else MaterialTheme.typography.bodySmall,
                                color = Color.White,
                            )
                        }
                    }
                )
            }

            val enabledCallsign = vessel.callSign != null
            if (enabledCallsign) {
                IndicatorButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    buttonColor = MarineBlueLight,
                    textColor = Color.White,
                    horizontalArrangement = Arrangement.Start,
                    width = Dp.Unspecified,
                    height = cellHeight,
                    enabled = enabledCallsign,
                    onClick = {
                        routePlatformLink("https://www.startpage.com/do/dsearch?query=callsign%20${vessel.callSign}")
                    },
                    content = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
                        ) {
                            Text(
                                modifier = Modifier
                                    .width(labelWidth),
                                text = stringResource(Res.string.label_callsign),
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                            )

                            Text(
                                modifier = Modifier
                                    .width(valueWidth),
                                text = vessel.callSign ?: "?",
                                textAlign = TextAlign.Start,
                                style = if (isHovered) MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.Underline) else MaterialTheme.typography.bodySmall,
                                color = Color.White,
                            )
                        }
                    }
                )
            }

            if (vessel.maximumStaticDraught != null) {
                Row(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraSmall)
                        .background(MarineBlueLighter)
                        .fillMaxWidth()
                        .height(cellHeight)
                        .padding(MaterialTheme.shapes.gap),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
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
                        text = "${vessel.maximumStaticDraught} m",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            if (vessel.totalLength != null) {
                Row(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraSmall)
                        .background(MarineBlueLighter)
                        .fillMaxWidth()
                        .height(cellHeight)
                        .padding(MaterialTheme.shapes.gap),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
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
                        text = "${vessel.totalLength} m",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            if (vessel.totalWidth != null) {
                Row(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraSmall)
                        .background(MarineBlueLighter)
                        .fillMaxWidth()
                        .height(cellHeight)
                        .padding(MaterialTheme.shapes.gap),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
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
                        text = "${vessel.totalWidth} m",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }


            if (vessel.hasSafetyMessage) {
                Row(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraSmall)
                        .background(Color.Red)
                        .fillMaxWidth()
                        .height(cellHeight)
                        .padding(MaterialTheme.shapes.gap),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
                ) {
                    Icon(
                        modifier = Modifier
                            .height(30.dp)
                            .rotate(vessel.heading.toFloat()),
                        painter = painterResource(Res.drawable.icon_warning_24px),
                        contentDescription = null,
                        tint = Color.White
                    )
                    Text(
                        modifier = Modifier
                            .width(valueWidth),
                        text = vessel.text?:"?",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }

                Row(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraSmall)
                        .background(Color.Red)
                        .fillMaxWidth()
                        .height(cellHeight)
                        .padding(MaterialTheme.shapes.gap),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
                ) {
                    Icon(
                        modifier = Modifier
                            .height(30.dp)
                            .rotate(vessel.heading.toFloat()),
                        painter = painterResource(Res.drawable.icon_my_location_24px),
                        contentDescription = null,
                        tint = Color.White
                    )
                    Text(
                        modifier = Modifier
                            .width(valueWidth),
                        text = vessel.location.toDmsString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }
            }
        }
    }
}
