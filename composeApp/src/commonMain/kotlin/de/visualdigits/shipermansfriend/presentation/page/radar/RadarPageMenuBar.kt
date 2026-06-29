package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.visualdigits.common.presentation.components.button.IndicatorButton
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_arrow_back_24px
import de.visualdigits.compose.resources.icon_directions_boat_24px
import de.visualdigits.compose.resources.icon_support_24px
import de.visualdigits.compose.resources.icon_zoom_in_24px
import de.visualdigits.compose.resources.icon_zoom_out_24px
import de.visualdigits.compose.resources.label_zoom
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.util.formatDistance
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun RadarPageMenuBar(
    currentRadarRadius: Double,
    setCurrentRadarRadius: (Double) -> Unit,
    radiusInner: Double,
    selectedVessel: AisDataUi,
    vesselNumber: Int,
    safetyDeviceNumber: Int,
    onAction: (ShipermansFriendAction) -> Unit
) {
    // Zoom Out (Mehr sehen = Radius vergrößern)
    val onZoomOut = {
        setCurrentRadarRadius((currentRadarRadius * 1.5).coerceAtMost(radiusInner))
    }

    // Zoom In (Näher ran = Radius verkleinern)
    val onZoomIn = {
        setCurrentRadarRadius((currentRadarRadius * 0.75).coerceAtLeast(200.0))
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Max),
        ) {
            Text(
                text = selectedVessel.safetyNote?.let {sn -> stringResource((sn))}?:selectedVessel.name,
                style = MaterialTheme.typography.labelMedium
            )

            Text(
                text = "${stringResource(Res.string.label_zoom)} ${currentRadarRadius.formatDistance()}",
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Row(
            modifier = Modifier
                .width(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.icon_directions_boat_24px),
                contentDescription = null,
                tint = TextColor
            )

            Text(
                modifier = Modifier
                    .width(IntrinsicSize.Max),
                text = vesselNumber.toString(),
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.icon_support_24px),
                contentDescription = null,
                tint = if (safetyDeviceNumber > 0) Color.Red else TextColor
            )

            Text(
                modifier = Modifier
                    .width(IntrinsicSize.Max),
                text = safetyDeviceNumber.toString(),
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium,
                color = if (safetyDeviceNumber > 0) Color.Red else TextColor
            )
        }

        IndicatorButton(
            buttonColor = MarineBlue,
            width = 50.dp,
            height = 50.dp,
            leadingIcon = painterResource(Res.drawable.icon_arrow_back_24px),
            leadingIconTint = Color.White,
            onClick = {
                onAction(ShipermansFriendAction.OnShowRadarBack())
            }
        )

        IndicatorButton(
            buttonColor = MarineBlue,
            width = 50.dp,
            height = 50.dp,
            leadingIcon = painterResource(Res.drawable.icon_zoom_out_24px),
            leadingIconTint = Color.White,
            onClick = {
                onZoomOut()
            }
        )

        IndicatorButton(
            buttonColor = MarineBlue,
            width = 50.dp,
            height = 50.dp,
            leadingIcon = painterResource(Res.drawable.icon_zoom_in_24px),
            leadingIconTint = Color.White,
            onClick = {
                onZoomIn()
            }
        )
    }
}
