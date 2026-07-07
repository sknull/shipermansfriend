package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.util.color
import de.visualdigits.common.presentation.components.button.IndicatorButton
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_my_location_24px
import de.visualdigits.compose.resources.icon_warning_24px
import de.visualdigits.compose.resources.label_callsign
import de.visualdigits.compose.resources.label_destination
import de.visualdigits.compose.resources.label_distance
import de.visualdigits.compose.resources.label_knots
import de.visualdigits.compose.resources.label_last_message
import de.visualdigits.compose.resources.label_length
import de.visualdigits.compose.resources.label_maxDraught
import de.visualdigits.compose.resources.label_minutes
import de.visualdigits.compose.resources.label_moored
import de.visualdigits.compose.resources.label_speed
import de.visualdigits.compose.resources.label_width
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueLight
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueLighter
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import de.visualdigits.shipermansfriend.presentation.style.gap
import de.visualdigits.shipermansfriend.presentation.util.routePlatformLink
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun VesselCard(
    state: ShipermansFriendState,
    sizeFactor: Float,
    vessel: AisDataUi,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val isLandscape = state.screenWidth > state.screenHeight
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val cellHeight = 30.dp * sizeFactor

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .dropShadow(
                shape = MaterialTheme.shapes.small,
                shadow = Shadow(
                    radius = 3.dp,
                    spread = 0.dp,
                    color = Color.Black.copy(alpha = 0.5f),
                    offset = DpOffset(3.dp, 3.dp)
                )
            )
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            val containerWidth = maxWidth
            val rowWidth = if (isLandscape) {
                (maxWidth - MaterialTheme.shapes.gap * 3) / 3
            } else {
                (maxWidth - MaterialTheme.shapes.gap * 3) / 2
            }

            FlowRow (
                modifier = Modifier
                    .width(containerWidth)
                    .padding(MaterialTheme.shapes.gap / 2),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
            ) {
                VesselNameRow(
                    sizeFactor = sizeFactor,
                    vessel = vessel
                )

                VesselButtonRow(
                    state = state,
                    vessel = vessel,
                    onAction = onAction
                )

                Row(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraSmall)
                        .background(MarineBlueLighter)
                        .width(rowWidth)
                        .height(cellHeight)
                        .padding(MaterialTheme.shapes.gap / 2),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.label_distance),
                        style = MaterialTheme.typography.labelSmall,
                    )

                    Text(
                        text = vessel.distanceString,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Row(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraSmall)
                        .background(MarineBlueLighter)
                        .width(rowWidth)
                        .height(cellHeight)
                        .padding(MaterialTheme.shapes.gap / 2),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.label_speed),
                        style = MaterialTheme.typography.labelSmall,
                    )

                    if (!vessel.isMoored) {
                        Text(
                            text = "${vessel.sog} ${stringResource(Res.string.label_knots)}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = vessel.speedKmh,
                            style = MaterialTheme.typography.bodySmall
                        )
                    } else {
                        Text(
                            text = stringResource(Res.string.label_moored),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraSmall)
                        .background(MarineBlueLighter)
                        .width(rowWidth)
                        .height(cellHeight)
                        .padding(MaterialTheme.shapes.gap / 2),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.label_last_message),
                        style = MaterialTheme.typography.labelSmall,
                    )
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = "${KmpOffsetDateTime.now().minus(vessel.timeUtc).inWholeMinutes} ${stringResource(Res.string.label_minutes)}",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

                if (vessel.destination?.isNotBlank() == true) {
                    Row(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.extraSmall)
                            .background(MarineBlueLighter)
                            .width(rowWidth)
                            .height(cellHeight)
                            .padding(MaterialTheme.shapes.gap / 2),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(Res.string.label_destination),
                            style = MaterialTheme.typography.labelSmall,
                        )
                        Text(
                            modifier = Modifier
                                .weight(1f),
                            text = vessel.destination,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }

                IndicatorButton(
                    buttonColor = MarineBlueLight,
                    textColor = Color.White,
                    horizontalArrangement = Arrangement.Start,
                    padding = MaterialTheme.shapes.gap / 2,
                    width = rowWidth,
                    height = cellHeight,
                    onClick = {
                        routePlatformLink("https://www.startpage.com/do/dsearch?query=mmsi%20${
                            vessel.mmsi.toString().padStart(9, '0')
                        }")
                    },
                    content = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(cellHeight),
                            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "MMSI",
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White
                            )

                            Text(
                                modifier = Modifier
                                    .weight(1f),
                                text = vessel.mmsi.toString(),
                                textAlign = TextAlign.Start,
                                style = if (isHovered) MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.Underline) else MaterialTheme.typography.bodySmall,
                                color = Color.White
                            )
                        }
                    }
                )

                if (vessel.imoNumber?.equals(0L) == false) {
                    IndicatorButton(
                        buttonColor = MarineBlueLight,
                        textColor = Color.White,
                        horizontalArrangement = Arrangement.Start,
                        padding = MaterialTheme.shapes.gap / 2,
                        width = rowWidth,
                        height = cellHeight,
                        onClick = {
                            routePlatformLink("https://www.startpage.com/do/dsearch?query=imo%20${vessel.imoNumber}")
                        },
                        content = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "IMO",
                                    textAlign = TextAlign.Start,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White,
                                )

                                Text(
                                    modifier = Modifier
                                        .weight(1f),
                                    text = vessel.imoNumber.toString(),
                                    textAlign = TextAlign.Start,
                                    style = if (isHovered) MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.Underline) else MaterialTheme.typography.bodySmall,
                                    color = Color.White,
                                )
                            }
                        }
                    )
                }

                if (vessel.callSign?.isNotBlank() == true) {
                    IndicatorButton(
                        buttonColor = MarineBlueLight,
                        textColor = Color.White,
                        horizontalArrangement = Arrangement.Start,
                        padding = MaterialTheme.shapes.gap / 2,
                        width = rowWidth,
                        height = cellHeight,
                        onClick = {
                            routePlatformLink("https://www.startpage.com/do/dsearch?query=callsign%20${vessel.callSign}")
                        },
                        content = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(Res.string.label_callsign),
                                    textAlign = TextAlign.Start,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White,
                                )

                                Text(
                                    modifier = Modifier
                                        .weight(1f),
                                    text = vessel.callSign,
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
                            .width(rowWidth)
                            .height(cellHeight)
                            .padding(MaterialTheme.shapes.gap / 2),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(Res.string.label_maxDraught),
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            modifier = Modifier
                                .weight(1f),
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
                            .width(rowWidth)
                            .height(cellHeight)
                            .padding(MaterialTheme.shapes.gap / 2),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(Res.string.label_length),
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            modifier = Modifier
                                .weight(1f),
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
                            .width(rowWidth)
                            .height(cellHeight)
                            .padding(MaterialTheme.shapes.gap / 2),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(Res.string.label_width),
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            modifier = Modifier
                                .weight(1f),
                            text = "${vessel.totalWidth} m",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                if (vessel.hasSafetyMessage) {
                    val messageSeverity = vessel.messageSeverity

                    Row(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.extraSmall)
                            .background(messageSeverity.color())
                            .fillMaxWidth()
                            .height(cellHeight)
                            .padding(MaterialTheme.shapes.gap / 2),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .height(30.dp - 5.dp),
                            painter = painterResource(Res.drawable.icon_warning_24px),
                            contentDescription = null,
                            tint = if (messageSeverity == Severity.Error) Color.White else TextColor
                        )
                        Text(
                            text = vessel.decodedText(),
                            style = MaterialTheme.typography.bodySmall,
                            color = if (messageSeverity == Severity.Error) Color.White else TextColor
                        )
                    }

                    Row(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.extraSmall)
                            .background(messageSeverity.color())
                            .fillMaxWidth()
                            .height(cellHeight)
                            .padding(MaterialTheme.shapes.gap / 2),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .height(30.dp - 5.dp),
                            painter = painterResource(Res.drawable.icon_my_location_24px),
                            contentDescription = null,
                            tint = if (messageSeverity == Severity.Error) Color.White else TextColor
                        )
                        Text(
                            text = vessel.location.toDmsString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = if (messageSeverity == Severity.Error) Color.White else TextColor
                        )
                    }
                }
            }
        }
    }
}
