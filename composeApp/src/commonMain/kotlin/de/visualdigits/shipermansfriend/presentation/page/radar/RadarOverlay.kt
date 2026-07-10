package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import de.visualdigits.compose.resources.icon_menu_24px
import de.visualdigits.compose.resources.icon_zoom_in_24px
import de.visualdigits.compose.resources.icon_zoom_out_24px
import de.visualdigits.compose.resources.label_knots
import de.visualdigits.compose.resources.label_moored
import de.visualdigits.compose.resources.label_zoom
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.util.capitalizeWords
import de.visualdigits.shipermansfriend.domain.util.formatDistance
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.page.ConnectivityIndicators
import de.visualdigits.shipermansfriend.presentation.page.ViewParameterIndicators
import de.visualdigits.shipermansfriend.presentation.style.RadarButtons
import de.visualdigits.shipermansfriend.presentation.style.RadarGrid
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.math.roundToInt

@Composable
fun RadarOverlay(
    viewModel: ShipermansFriendViewModel,
    state: ShipermansFriendState,
    sizeFactor: Float,
    currentRadarRadius: Double,
    setCurrentRadarRadius: (Double) -> Unit,
    radiusOuter: Double,
    vessel: AisDataUi?,
    vesselNumber: Int,
    safetyDeviceNumber: Int,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val onZoomOut = {
        setCurrentRadarRadius((currentRadarRadius * 1.5).coerceAtMost(radiusOuter))
    }

    val onZoomIn = {
        setCurrentRadarRadius((currentRadarRadius * 0.75).coerceAtLeast(200.0))
    }

    val isExpanded = state.collapsibleState["radar_legend"] == true

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.shapes.gap),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
        ) {
            if (vessel != null) {
                val speedLabel = if (!vessel.isMoored) {
                    "${vessel.sog} ${stringResource(Res.string.label_knots)} rot=${vessel.rateOfTurnDegreesPerMinute.roundToInt()}"
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
                        text = vessel.name.capitalizeWords(),
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
            IndicatorButton(
                buttonColor = RadarButtons,
                width = 30.dp,
                height = 30.dp,
                leadingIcon = painterResource(Res.drawable.icon_menu_24px),
                leadingIconTint = if (isExpanded) Color.White else RadarGrid,
                onClick = {
                    onAction(ShipermansFriendAction.OnCollapsibleStateChange("radar_legend", !isExpanded))
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Spacer(Modifier.weight(1f))

            ConnectivityIndicators(
                modifier = Modifier
                    .padding(end = 3.dp),
                viewModel = viewModel,
                sizeFactor = sizeFactor,
                iconColor = RadarGrid
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.TopEnd
        ) {
            if (isExpanded) {
                RadarLegendBox(
                    sizeFactor = sizeFactor,
                    state = state,
                    onAction = onAction
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
            verticalAlignment = Alignment.Bottom
        ) {
            ViewParameterIndicators(
                viewModel = viewModel,
                state = state,
                sizeFactor = sizeFactor,
                color = RadarGrid,
                vesselNumber = vesselNumber,
                safetyDeviceNumber = safetyDeviceNumber,
                zoomColor = Color.Yellow
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = "${stringResource(Res.string.label_zoom)}: ${currentRadarRadius.formatDistance()}",
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium,
                color = RadarGrid
            )

        }
    }
}
