package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import de.visualdigits.common.domain.util.copyFactor
import de.visualdigits.common.presentation.components.modifier.angledInnerShadow
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.label_knots
import de.visualdigits.compose.resources.label_moored
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.style.LightGray
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun HoveredVesselBox(
    modifier: Modifier = Modifier,
    activeHoverVesselState: MutableState<AisDataUi?>
) {
    val vessel = activeHoverVesselState.value
    if (vessel != null) {
        Box(
            modifier = modifier
                .width(300.dp)
                .height(40.dp)
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
            val speedLabel = if (vessel.sog > 0.5) {
                "${vessel.sog} ${stringResource(Res.string.label_knots)}"
            } else {
                stringResource(Res.string.label_moored)
            }
            Row(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraSmall)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .height(IntrinsicSize.Min)
                        .padding(MaterialTheme.shapes.gap),
                    text = "${vessel.name} [$speedLabel]",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1
                )

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(40.dp)
                        .background(MarineBlue)
                        .angledInnerShadow(
                            angle = 135f,
                            distance = 10.dp,
                            alpha = 0.5f,
                            insetSize = 2.dp,
                            insetColorLight = MarineBlue.copyFactor(valueFactor = 1.5f),
                            insetColorShadow = MarineBlue.copyFactor(valueFactor = 0.75f)
                        )
                        .padding(MaterialTheme.shapes.gap),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .height(IntrinsicSize.Min),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
                    ) {
                        vessel.shipType?.category?.icon?.let { icon ->
                            Icon(
                                modifier = Modifier
                                    .height(30.dp),
                                painter = painterResource(icon),
                                contentDescription = vessel.shipType.category.name,
                                tint = LightGray,
                            )
                        }

                        Text(
                            modifier = Modifier,
                            text = vessel.shipType?.category?.name?:"?",
                            style = MaterialTheme.typography.bodySmall,
                            color = LightGray
                        )
                    }
                }
            }
        }
    }
}
