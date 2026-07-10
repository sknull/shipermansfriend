package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.util.color
import de.visualdigits.common.presentation.components.Led
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_direction_24px
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.MovementDirection
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipCategory
import de.visualdigits.shipermansfriend.domain.model.settings.SK
import de.visualdigits.shipermansfriend.domain.util.capitalizeWords
import de.visualdigits.shipermansfriend.domain.util.parseDistance
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.model.VesselsMode
import de.visualdigits.shipermansfriend.presentation.style.LightGray
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueEvenLighter
import de.visualdigits.shipermansfriend.presentation.style.RedAlert
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import de.visualdigits.shipermansfriend.presentation.style.YellowAlert
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.painterResource
import kotlin.math.absoluteValue

@Composable
fun VesselNameRow(
    modifier: Modifier = Modifier,
    viewModel: ShipermansFriendViewModel,
    state: ShipermansFriendState,
    sizeFactor: Float,
    vessel: AisDataUi,
    location: Location?,
    vesselsMode: VesselsMode
) {
    val innerRadius by viewModel.innerRadius.collectAsStateWithLifecycle()
    val warningDistance = state.settings?.get<String>(SK.warningDistance)?.parseDistance() ?: 10000.0

    val shipType = vessel.shipType
    val iconHeight = 25.dp * sizeFactor

    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .fillMaxWidth()
            .height(40.dp)
            .background(MarineBlueEvenLighter),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
        verticalAlignment = Alignment.Top
    ) {
        Row(
            modifier = modifier
                .weight(1f)
                .padding(MaterialTheme.shapes.gap),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .height(iconHeight),
                painter = painterResource(vessel.mmsiCountryPrefix.country.flag),
                contentDescription = vessel.mmsiCountryPrefix.country.countryName,
                contentScale = ContentScale.Fit,
            )

            Column(
                modifier = Modifier
            ) {
                Row(
                    modifier = modifier
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val vesselName = vessel.name.capitalizeWords()
                    if (vesselName.isNotBlank()) {
                        Text(
                            text = vesselName,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Text(
                        text = vessel.mmsiCountryPrefix.country.countryName,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Row(
                    modifier = modifier
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier,
                        text = vessel.shipType.category.name,
                        style = MaterialTheme.typography.bodySmall,
                    )

                    Led(
                        radius = 6.dp,
                        colorOn = if (vessel.messageSeverity > Severity.Info) vessel.messageSeverity.color() else shipType.category.color
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            Icon(
                modifier = Modifier
                    .height(iconHeight)
                    .rotate(vessel.heading.toFloat()),
                painter = painterResource(Res.drawable.icon_direction_24px),
                contentDescription = null,
                tint = TextColor
            )
            val (movementDirection, tint) = determineColor(innerRadius, location, vessel, warningDistance, vesselsMode)
            Icon(
                modifier = Modifier
                    .height(iconHeight),
                painter = painterResource(movementDirection.icon),
                contentDescription = null,
                tint = tint
            )
        }

        Box(
            modifier = Modifier
                .width(60.dp)
                .fillMaxHeight()
                .background(MarineBlue)
                .padding(MaterialTheme.shapes.gap),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .width(50.dp)
                    .height(30.dp),
                painter = painterResource(vessel.shipType.category.icon),
                contentDescription = vessel.shipType.category.name,
                contentScale = ContentScale.Fit,
                colorFilter = if (vessel.shipType.category != ShipCategory.SafetyDevice) {
                    ColorFilter.tint(LightGray)
                } else {
                    null
                }
            )
        }
    }
}

/**
 * - when a vessel is otbounding, and it's distance to the perimeter is less than the warning distance: yellow alert
 * - when we are either in search mode or in safety mode only warn if the vessel is inbounding:
 *   - when the distance to the perimeter is less than the warning distance: yellow alert
 *   - wwhen the vessel is inside the perimeter: red alert
 * - otherwise normal text color
 */
private fun determineColor(
    innerRadius: Double?,
    location: Location?,
    vessel: AisDataUi,
    warningDistance: Double,
    vesselsMode: VesselsMode
): Pair<MovementDirection, Color> {
    val radius = innerRadius ?: 1000.0
    val movementDirection = location?.let { l -> vessel.movementDirection(l) } ?: MovementDirection.UNKNOWN
    val isInPerimeter = vessel.distance < radius
    val distanceToPerimeter = (vessel.distance - radius).absoluteValue
    val tint =
        if (movementDirection == MovementDirection.OUTBOUND && isInPerimeter && distanceToPerimeter < warningDistance) {
            YellowAlert
        } else if (vesselsMode == VesselsMode.SEARCH || vesselsMode == VesselsMode.SAFETY) {
            if (movementDirection == MovementDirection.INBOUND && !isInPerimeter && distanceToPerimeter < warningDistance) {
                YellowAlert
            } else if (isInPerimeter) {
                RedAlert
            } else {
                TextColor
            }
        } else {
            TextColor
        }
    return Pair(movementDirection, tint)
}
