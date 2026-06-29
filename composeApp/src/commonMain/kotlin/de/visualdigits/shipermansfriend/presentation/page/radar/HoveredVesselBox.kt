package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import de.visualdigits.common.presentation.components.util.conditional
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.label_knots
import de.visualdigits.compose.resources.label_moored
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.style.ButtonsDark
import de.visualdigits.shipermansfriend.presentation.style.ButtonsDarker
import de.visualdigits.shipermansfriend.presentation.style.DarkRed
import de.visualdigits.shipermansfriend.presentation.style.LightGray
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun HoveredVesselBox(
    modifier: Modifier = Modifier,
    activeHoverVesselState: MutableState<List<AisDataUi>>
) {
    val vessels = activeHoverVesselState.value
    if (vessels.isNotEmpty()) {
        BoxWithConstraints(
            modifier = modifier
                .fillMaxHeight()
        ) {
            val maxEntries = (maxHeight / (30.dp + MaterialTheme.shapes.gap / 2)).toInt() - 1
            Box(
                modifier = modifier
                    .width(IntrinsicSize.Min)
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
                        .width(IntrinsicSize.Min)
                        .background(MaterialTheme.colorScheme.surface),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
                ) {
                    vessels.take(maxEntries).forEach { vessel ->
                        val speedLabel = if (vessel.sog > 0.5) {
                            "${vessel.sog} ${stringResource(Res.string.label_knots)}"
                        } else {
                            stringResource(Res.string.label_moored)
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp)
                                .conditional(vessel.hasSafetyMessage) { background(Color.Red) }
                                .conditional(!vessel.isMoored && !vessel.hasSafetyMessage) { background(ButtonsDark) }
                                .conditional(vessel.isMoored && !vessel.hasSafetyMessage) { background(ButtonsDarker) },
                            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(MaterialTheme.shapes.gap / 2),
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

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .width(IntrinsicSize.Max)
                                            .fillMaxHeight()
                                            .padding(MaterialTheme.shapes.gap / 2),
                                        text = "[${vessel.mmsiCountryPrefix.country.countryName}] ${vessel.safetyNote?.let {sn -> stringResource((sn))}?:vessel.name} ${vessel.distanceString} [$speedLabel]",
                                        style = if (vessel.hasSafetyMessage || !vessel.isMoored) MaterialTheme.typography.labelSmall else  MaterialTheme.typography.bodySmall,
                                        maxLines = 1,
                                        color = if (vessel.hasSafetyMessage || vessel.isMoored) Color.White else TextColor
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .width(40.dp)
                                    .fillMaxHeight()
                                    .conditional(!vessel.hasSafetyMessage) { background(MarineBlue) }
                                    .conditional(vessel.hasSafetyMessage) { background(DarkRed) }
                                    .padding(MaterialTheme.shapes.gap / 2),
                                contentAlignment = Alignment.Center
                            ) {
                                vessel.shipType?.category?.icon?.let { icon ->
                                    Icon(
                                        modifier = Modifier
                                            .height(24.dp),
                                        painter = painterResource(icon),
                                        contentDescription = vessel.shipType.category.name,
                                        tint = LightGray
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
