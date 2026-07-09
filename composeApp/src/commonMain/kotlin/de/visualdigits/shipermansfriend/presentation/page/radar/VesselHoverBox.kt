package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.util.color
import de.visualdigits.common.presentation.components.util.conditional
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.label_knots
import de.visualdigits.compose.resources.label_moored
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipCategory
import de.visualdigits.shipermansfriend.domain.util.capitalizeWords
import de.visualdigits.shipermansfriend.domain.util.formatTime
import de.visualdigits.shipermansfriend.presentation.style.ButtonsDark
import de.visualdigits.shipermansfriend.presentation.style.ButtonsDarker
import de.visualdigits.shipermansfriend.presentation.style.LightGray
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.RedDark
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import de.visualdigits.shipermansfriend.presentation.style.YellowDark
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.math.roundToInt

@Composable
fun VesselHoverBox(
    modifier: Modifier = Modifier,
    currentTime: KmpOffsetDateTime,
    activeHoverVesselState: MutableState<List<AisDataUi>>
) {
    val vessels = activeHoverVesselState.value
    if (vessels.isNotEmpty()) {
        BoxWithConstraints(
            modifier = modifier
                .fillMaxHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            val maxEntries = (maxHeight / (31.dp)).toInt() - 1
            Box(
                modifier = modifier
                    .dropShadow(
                        shape = MaterialTheme.shapes.extraSmall,
                        shadow = Shadow(
                            radius = 2.dp,
                            spread = 2.dp,
                            color = Color.Black.copy(alpha = 0.5f),
                            offset = DpOffset(2.dp, 2.dp)
                        )
                    )
            ) {
                Column(
                    modifier = modifier
                        .clip(MaterialTheme.shapes.extraSmall)
                        .background(MaterialTheme.colorScheme.surface),
                    verticalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    vessels.take(maxEntries).forEach { vessel ->
                        val speedLabel = if (!vessel.isMoored) {
                            "${vessel.sog} ${stringResource(Res.string.label_knots)} rot=${vessel.rateOfTurnDegreesPerMinute.roundToInt()}"
                        } else {
                            stringResource(Res.string.label_moored)
                        }
                        val timeLabel = currentTime.minus(vessel.timeUtc).formatTime()
                        val messageSeverity = vessel.messageSeverity
                        val height = if (vessel.hasSafetyMessage) 60.dp else 30.dp
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(height)
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(height)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(min = 30.dp)
                                        .conditional(messageSeverity > Severity.Info) { background(messageSeverity.color()) }
                                        .conditional(!vessel.isMoored && messageSeverity == Severity.Info) { background(ButtonsDark) }
                                        .conditional(vessel.isMoored && messageSeverity == Severity.Info) { background(ButtonsDarker) }
                                        .padding(MaterialTheme.shapes.gap / 4),
                                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .width(30.dp),
                                        painter = painterResource(vessel.mmsiCountryPrefix.country.flag),
                                        contentDescription = null,
                                        contentScale = ContentScale.Fit,
                                    )

                                    Text(
                                        text = "[${vessel.mmsiCountryPrefix.country.countryName}] ${vessel.safetyNote?.let {sn -> stringResource((sn))}?:vessel.name.capitalizeWords()} ${vessel.distanceString} [$speedLabel] $timeLabel",
                                        style = if (vessel.hasSafetyMessage || !vessel.isMoored) MaterialTheme.typography.labelSmall else  MaterialTheme.typography.bodySmall,
                                        softWrap = true,
                                        color = if (vessel.hasSafetyMessage || vessel.isMoored) Color.White else TextColor
                                    )
                                }

                                if (vessel.hasSafetyMessage) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .heightIn(min = 30.dp)
                                            .conditional(messageSeverity > Severity.Info) { background(messageSeverity.color()) }
                                            .conditional(!vessel.isMoored && messageSeverity == Severity.Info) { background(ButtonsDark) }
                                            .conditional(vessel.isMoored && messageSeverity == Severity.Info) { background(ButtonsDarker) }
                                            .padding(MaterialTheme.shapes.gap / 4),
                                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "${vessel.decodedText()} ${vessel.location.toDmsString()}",
                                            style = MaterialTheme.typography.labelSmall,
                                            softWrap = true,
                                            color = if (messageSeverity == Severity.Error) Color.White else TextColor
                                        )
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .width(40.dp)
                                    .fillMaxHeight()
                                    .conditional(messageSeverity == Severity.Error) { background(RedDark) }
                                    .conditional(messageSeverity == Severity.Warn) { background(YellowDark) }
                                    .conditional(messageSeverity == Severity.Info) { background(MarineBlue) },
                                contentAlignment = Alignment.Center
                            ) {
                                if (vessel.shipType.category != ShipCategory.SafetyDevice) {
                                    Icon(
                                        modifier = Modifier
                                            .height(26.dp),
                                        painter = painterResource(vessel.shipType.category.icon),
                                        contentDescription = vessel.shipType.category.name,
                                        tint = LightGray,
                                    )
                                } else {
                                    Image(
                                        modifier = Modifier
                                            .height(26.dp),
                                        painter = painterResource(vessel.shipType.category.icon),
                                        contentDescription = null,
                                        contentScale = ContentScale.Fit,
                                    )
                                }
                            }
                        }
                    }

                    if (vessels.size > maxEntries) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(MaterialTheme.shapes.gap / 2),
                            text = "...",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
