package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.presentation.components.button.IndicatorButton
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.label_callsign
import de.visualdigits.compose.resources.label_destination
import de.visualdigits.compose.resources.label_distance
import de.visualdigits.compose.resources.label_knots
import de.visualdigits.compose.resources.label_last_message
import de.visualdigits.compose.resources.label_length
import de.visualdigits.compose.resources.label_maxDraught
import de.visualdigits.compose.resources.label_moored
import de.visualdigits.compose.resources.label_speed
import de.visualdigits.compose.resources.label_turnRate
import de.visualdigits.compose.resources.label_width
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.util.formatDistance
import de.visualdigits.shipermansfriend.domain.util.formatTime
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueLight
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueLighter
import de.visualdigits.shipermansfriend.presentation.style.gap
import de.visualdigits.shipermansfriend.presentation.util.routePlatformLink
import org.jetbrains.compose.resources.stringResource
import kotlin.math.roundToInt

@Composable
fun VesselDataFieldsStandard(
    rowWidth: Dp,
    cellHeight: Dp,
    vessel: AisDataUi,
    isHovered: Boolean,
    currentTime: KmpOffsetDateTime,
    location: Location?
) {
    // distance
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
            text = location?.let { l -> vessel.extrapolateDistance(currentTime, l).formatDistance() } ?: vessel.distanceString,
            style = MaterialTheme.typography.bodySmall
        )
    }

    // speed
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
                text = "${vessel.sog} ${stringResource(Res.string.label_knots)} / ${vessel.speedKmh} Km/h",
                style = MaterialTheme.typography.bodySmall
            )
        } else {
            Text(
                text = stringResource(Res.string.label_moored),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }

    // rate of turn
    if (vessel.rateOfTurnDegreesPerMinute != 0.0) {
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
                text = stringResource(Res.string.label_turnRate),
                style = MaterialTheme.typography.labelSmall,
            )

            Text(
                text = "${vessel.rateOfTurnDegreesPerMinute.roundToInt()} °/minute",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }

    // last messsage
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
            text = currentTime.minus(vessel.timeUtc).formatTime(),
            style = MaterialTheme.typography.bodySmall,
        )
    }

    // destination
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

    // mmsi
    IndicatorButton(
        buttonColor = MarineBlueLight,
        textColor = Color.White,
        horizontalArrangement = Arrangement.Start,
        padding = MaterialTheme.shapes.gap / 2,
        width = rowWidth,
        height = cellHeight,
        onClick = {
            routePlatformLink(
                "https://www.startpage.com/do/dsearch?query=mmsi%20${
                    vessel.mmsi.toString().padStart(9, '0')
                }"
            )
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

    // imo number
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

    // call sign
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

    // maximum draught
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

    // total length
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

    // total width
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
}
