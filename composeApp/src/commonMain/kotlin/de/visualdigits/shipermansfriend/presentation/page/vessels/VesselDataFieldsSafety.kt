package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.util.color
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_my_location_24px
import de.visualdigits.compose.resources.icon_warning_24px
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.painterResource

@Composable
fun VesselDataFieldsSafety(
    vessel: AisDataUi,
    cellHeight: Dp
) {
    if (vessel.hasSafetyMessage) {
        val messageSeverity = vessel.messageSeverity

        Row(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraSmall)
                .background(messageSeverity.color())
                .fillMaxWidth()
                .heightIn(min = cellHeight)
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
                .heightIn(min = cellHeight)
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
