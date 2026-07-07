package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import de.visualdigits.compose.resources.icon_menu_24px
import de.visualdigits.compose.resources.icon_support_24px
import de.visualdigits.compose.resources.icon_zoom_in_24px
import de.visualdigits.compose.resources.icon_zoom_out_24px
import de.visualdigits.compose.resources.label_knots
import de.visualdigits.compose.resources.label_moored
import de.visualdigits.compose.resources.label_zoom
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipCategory
import de.visualdigits.shipermansfriend.domain.model.type.CategoryMode
import de.visualdigits.shipermansfriend.domain.util.capitalizeWords
import de.visualdigits.shipermansfriend.domain.util.formatDistance
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.style.RadarButtons
import de.visualdigits.shipermansfriend.presentation.style.RadarGrid
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun RadarOverlay(
    state: ShipermansFriendState,
    sizeFactor: Float,
    currentRadarRadius: Double,
    setCurrentRadarRadius: (Double) -> Unit,
    radiusInner: Double,
    selectedShipCategories: Map<ShipCategory, CategoryMode>,
    selectedVessel: AisDataUi?,
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

    val isExpanded = state.collapsibleState["radar_legend"] == true

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
            verticalAlignment = Alignment.Top
        ) {
            if (selectedVessel != null) {
                val speedLabel = if (!selectedVessel.isMoored) {
                    "${selectedVessel.sog} ${stringResource(Res.string.label_knots)}"
                } else {
                    stringResource(Res.string.label_moored)
                }

                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = (selectedVessel.safetyNote?.let { sn -> stringResource((sn))} ?: selectedVessel.name).capitalizeWords(),
                        maxLines = 1,
                        softWrap = false,
                        style = MaterialTheme.typography.labelMedium,
                        color = RadarGrid
                    )
                    Text(
                        text = speedLabel,
                        style = MaterialTheme.typography.bodyMedium,
                        color = RadarGrid
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            IndicatorButton(
                buttonColor = RadarButtons,
                width = 25.dp,
                height = 25.dp,
                leadingIcon = painterResource(Res.drawable.icon_arrow_back_24px),
                leadingIconTint = Color.White,
                onClick = {
                    onAction(ShipermansFriendAction.OnShowRadarBack())
                }
            )
            IndicatorButton(
                buttonColor = RadarButtons,
                width = 25.dp,
                height = 25.dp,
                leadingIcon = painterResource(Res.drawable.icon_zoom_out_24px),
                leadingIconTint = Color.White,
                onClick = {
                    onZoomOut()
                }
            )
            IndicatorButton(
                buttonColor = RadarButtons,
                width = 25.dp,
                height = 25.dp,
                leadingIcon = painterResource(Res.drawable.icon_zoom_in_24px),
                leadingIconTint = Color.White,
                onClick = {
                    onZoomIn()
                }
            )
            IndicatorButton(
                buttonColor = RadarButtons,
                width = 25.dp,
                height = 25.dp,
                leadingIcon = painterResource(Res.drawable.icon_menu_24px),
                leadingIconTint = if (isExpanded) Color.White else RadarGrid,
                onClick = {
                    onAction(ShipermansFriendAction.OnCollapsibleStateChange("radar_legend", !isExpanded))
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.TopEnd
        ) {
            if (isExpanded) {
                LegendBox(
                    sizeFactor = sizeFactor,
                    selectedShipCategories = selectedShipCategories,
                    onAction = onAction
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "${stringResource(Res.string.label_zoom)} ${currentRadarRadius.formatDistance()}",
                style = MaterialTheme.typography.labelMedium,
                color = RadarButtons
            )

            Spacer(Modifier.weight(1f))

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
}
