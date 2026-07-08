package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.common.domain.model.platform.ConnectivityMode
import de.visualdigits.common.presentation.components.button.IndicatorButton
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_my_location_24px
import de.visualdigits.compose.resources.icon_radar_24px
import de.visualdigits.compose.resources.icon_support_24px
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.AisStreamState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.page.ConnectivityIndicators
import de.visualdigits.shipermansfriend.presentation.page.ViewParameterIndicators
import de.visualdigits.shipermansfriend.presentation.style.IndicatorColor
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.gap
import de.visualdigits.shipermansfriend.presentation.util.routePlatformLink
import org.jetbrains.compose.resources.painterResource

@Composable
fun LocationBar(
    viewModel: ShipermansFriendViewModel,
    state: ShipermansFriendState,
    sizeFactor: Float,
    currentRadarRadius: Double,
    vesselNumber: Int,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val connectivityMode by viewModel.connectivityMode.collectAsStateWithLifecycle()
    val aisStreamState by viewModel.aisStreamState.collectAsStateWithLifecycle()
    val receivingDataState by viewModel.receivingDataState.collectAsStateWithLifecycle()
    val locationValue by viewModel.location.collectAsStateWithLifecycle()
    val safetyDevices by viewModel.safetyDevices.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .dropShadow(
                shape = RoundedCornerShape(8.dp),
                shadow = Shadow(
                    radius = 4.dp,
                    spread = 2.dp,
                    color = Color.Black.copy(alpha = 0.5f),
                    offset = DpOffset((5).dp, 5.dp)
                )
            )
    ) {
        Row(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(MaterialTheme.shapes.gap),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IndicatorButton(
                        modifier = Modifier
                            .weight(1f),
                        buttonColor = MarineBlue,
                        textColor = Color.White,
                        width = Dp.Unspecified,
                        height = 30.dp,
                        leadingIcon = painterResource(Res.drawable.icon_my_location_24px),
                        leadingIconTint = Color.White,
                        text = "${locationValue?.toDmsString()}",
                        textAlign = TextAlign.Start,
                        enabled = locationValue != null,
                        onClick = {
                            routePlatformLink("https://www.google.com/maps/search/?api=1&query=${locationValue?.latitude}%2C${locationValue?.longitude}")
                        }
                    )
                    IndicatorButton(
                        buttonColor = MarineBlue,
                        textColor = Color.White,
                        width = 30.dp,
                        height = 30.dp,
                        leadingIcon = painterResource(Res.drawable.icon_radar_24px),
                        leadingIconTint = Color.White,
                        text = "${locationValue?.toDmsString()}",
                        textAlign = TextAlign.Start,
                        enabled = locationValue != null,
                        onClick = {
                            onAction(ShipermansFriendAction.OnShowRadar())
                        }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ViewParameterIndicators(
                        viewModel = viewModel,
                        state = state,
                        sizeFactor = sizeFactor,
                        vesselNumber = vesselNumber,
                        safetyDeviceNumber = safetyDevices.size,
                        currentRadarRadius = currentRadarRadius
                    )

                    Spacer(Modifier.weight(1f))

                    ConnectivityIndicators(viewModel = viewModel, sizeFactor = sizeFactor)
                }
            }

            if (aisStreamState == AisStreamState.Down || connectivityMode == ConnectivityMode.disconnected) {
                IndicatorButton(
                    buttonColor = MarineBlue,
                    textColor = Color.White,
                    width = 50.dp,
                    height = 50.dp,
                    leadingIcon = painterResource(Res.drawable.icon_support_24px),
                    leadingIconTint = if (state.isReconnecting) IndicatorColor else Color.White,
                    onClick = {
                        onAction(ShipermansFriendAction.OnReconnect())
                    }
                )
            }
        }
    }
}
