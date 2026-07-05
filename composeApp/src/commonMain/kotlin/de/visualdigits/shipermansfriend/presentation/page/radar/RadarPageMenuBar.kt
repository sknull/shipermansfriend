package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import de.visualdigits.compose.resources.label_knots
import de.visualdigits.compose.resources.label_moored
import de.visualdigits.compose.resources.label_zoom
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.util.formatDistance
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.style.RadarButtons
import de.visualdigits.shipermansfriend.presentation.style.RadarGrid
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun RadarPageMenuBar(
    state: ShipermansFriendState,
    sizeFactor: Float,
    currentRadarRadius: Double,
    setCurrentRadarRadius: (Double) -> Unit,
    radiusInner: Double,
    selectedVessel: AisDataUi,
    vesselNumber: Int,
    safetyDeviceNumber: Int,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val onZoomOut = {
        setCurrentRadarRadius((currentRadarRadius * 1.5).coerceAtMost(radiusInner))
    }

    val onZoomIn = {
        setCurrentRadarRadius((currentRadarRadius * 0.75).coerceAtLeast(200.0))
    }

    val speedLabel = if (selectedVessel.sog >= 0.5) {
        " [${selectedVessel.sog} ${stringResource(Res.string.label_knots)}]"
    } else {
        " [${stringResource(Res.string.label_moored)}]"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
        ) {
            Text(
                text = "${selectedVessel.safetyNote?.let { sn -> stringResource((sn))}?:selectedVessel.name}$speedLabel",
                style = MaterialTheme.typography.labelMedium,
                color = RadarGrid
            )
            Text(
                text = "${stringResource(Res.string.label_zoom)} ${currentRadarRadius.formatDistance()}",
                style = MaterialTheme.typography.labelMedium,
                color = RadarButtons
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .width(20.dp * sizeFactor),
                    painter = painterResource(Res.drawable.icon_directions_boat_24px),
                    contentDescription = null,
                    tint = RadarButtons
                )
                Text(
                    modifier = Modifier,
                    text = vesselNumber.toString(),
                    maxLines = 1,
                    style = MaterialTheme.typography.labelMedium,
                    color = RadarButtons
                )

                Spacer(Modifier.width(MaterialTheme.shapes.gap / 2))

                Icon(
                    modifier = Modifier
                        .width(20.dp * sizeFactor),
                    painter = painterResource(Res.drawable.icon_support_24px),
                    contentDescription = null,
                    tint = if (safetyDeviceNumber > 0 && state.hasUnreadSafetyData) Color.Red else RadarButtons
                )
                Text(
                    modifier = Modifier,
                    text = safetyDeviceNumber.toString(),
                    maxLines = 1,
                    style = MaterialTheme.typography.labelMedium,
                    color = if (safetyDeviceNumber > 0 && state.hasUnreadSafetyData) Color.Red else RadarButtons
                )
            }
        }

        IndicatorButton(
            buttonColor = RadarButtons,
            width = 30.dp,
            height = 30.dp,
            leadingIcon = painterResource(Res.drawable.icon_arrow_back_24px),
            leadingIconTint = Color.White,
            onClick = {
                onAction(ShipermansFriendAction.OnShowRadarBack())
            }
        )

        IndicatorButton(
            buttonColor = RadarButtons,
            width = 30.dp,
            height = 30.dp,
            leadingIcon = painterResource(Res.drawable.icon_zoom_out_24px),
            leadingIconTint = Color.White,
            onClick = {
                onZoomOut()
            }
        )

        IndicatorButton(
            buttonColor = RadarButtons,
            width = 30.dp,
            height = 30.dp,
            leadingIcon = painterResource(Res.drawable.icon_zoom_in_24px),
            leadingIconTint = Color.White,
            onClick = {
                onZoomIn()
            }
        )
    }
}
