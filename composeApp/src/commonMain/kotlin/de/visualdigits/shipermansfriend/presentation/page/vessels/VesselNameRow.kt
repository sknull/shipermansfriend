package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_direction_24px
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipCategory
import de.visualdigits.shipermansfriend.domain.util.capitalizeWords
import de.visualdigits.shipermansfriend.presentation.style.LightGray
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueEvenLighter
import de.visualdigits.shipermansfriend.presentation.style.RedAlert
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import de.visualdigits.shipermansfriend.presentation.style.YellowAlert
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.painterResource

@Composable
fun VesselNameRow(
    modifier: Modifier = Modifier,
    vesselWarned: Boolean,
    vesselInInnerRadius: Boolean,
    vessel: AisDataUi
) {
    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .fillMaxWidth()
            .height(40.dp)
            .background(MarineBlueEvenLighter),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
        verticalAlignment = Alignment.CenterVertically
    ) {
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

        if (vessel.name.isNotBlank()) {
            Text(
                modifier = Modifier
                    .padding(MaterialTheme.shapes.gap / 2),
                text = vessel.name.capitalizeWords(),
                style = MaterialTheme.typography.labelMedium
            )
        }

        Text(
            modifier = Modifier
                .padding(MaterialTheme.shapes.gap / 2),
            text =   vessel.shipType.category.name,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(Modifier.weight(1f))

        Icon(
            modifier = Modifier
                .rotate(vessel.heading.toFloat())
                .padding(MaterialTheme.shapes.gap / 2),
            painter = painterResource(Res.drawable.icon_direction_24px),
            contentDescription = null,
            tint = TextColor
        )

        Icon(
            modifier = Modifier
                .padding(MaterialTheme.shapes.gap / 2),
            painter = painterResource(vessel.movementDirection.icon),
            contentDescription = null,
            tint = if (vesselWarned) {
                YellowAlert
            } else if (vesselInInnerRadius) {
                RedAlert
            } else {
                TextColor
            }
        )
    }
}
