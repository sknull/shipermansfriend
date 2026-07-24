package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.common.domain.model.platform.ConnectivityMode
import de.visualdigits.common.presentation.components.button.IndicatorButton
import de.visualdigits.common.presentation.components.util.conditional
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_move_location_24px
import de.visualdigits.compose.resources.icon_my_location_24px
import de.visualdigits.compose.resources.icon_radar_24px
import de.visualdigits.compose.resources.icon_support_24px
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.AisStreamState
import de.visualdigits.shipermansfriend.domain.util.formatTime
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.page.ConnectivityIndicators
import de.visualdigits.shipermansfriend.presentation.page.ViewParameterIndicators
import de.visualdigits.shipermansfriend.presentation.style.IndicatorColor
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueLight
import de.visualdigits.shipermansfriend.presentation.style.SandYellow
import de.visualdigits.shipermansfriend.presentation.style.gap
import de.visualdigits.shipermansfriend.presentation.util.routePlatformLink
import org.jetbrains.compose.resources.painterResource

@Composable
fun LocationBar(
    viewModel: ShipermansFriendViewModel,
    state: ShipermansFriendState,
    sizeFactor: Float,
    vesselNumber: Int,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val connectivityMode by viewModel.connectivityMode.collectAsStateWithLifecycle()
    val aisStreamState by viewModel.aisStreamState.collectAsStateWithLifecycle()
    val locationValue by viewModel.location.collectAsStateWithLifecycle()
    val safetyDevices by viewModel.safetyDevices.collectAsStateWithLifecycle()

    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .fillMaxWidth()
            .background(MarineBlue)
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
                Icon(
                    modifier = Modifier
                        .size(18.dp * sizeFactor),
                    painter = painterResource(Res.drawable.icon_my_location_24px),
                    contentDescription = null,
                    tint = Color.White
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .pointerHoverIcon(PointerIcon.Hand)
                        .clickable { routePlatformLink("https://www.google.com/maps/search/?api=1&query=${locationValue?.latitude}%2C${locationValue?.longitude}") },
                    text = "${locationValue?.toDmsString()}",
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                    color = SandYellow
                )
                IndicatorButton(
                    buttonColor = MarineBlueLight,
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
                    textColor = Color.White,
                    zoomColor = Color.White,
                    vesselNumber = vesselNumber,
                    safetyDeviceNumber = safetyDevices.size
                )

                Spacer(Modifier.weight(1f))

                ConnectivityIndicators(
                    viewModel = viewModel,
                    sizeFactor = sizeFactor,
                    backgroundColor = Color.Transparent
                )
            }
        }

        if (aisStreamState == AisStreamState.Down || connectivityMode == ConnectivityMode.disconnected) {
            IndicatorButton(
                buttonColor = MarineBlueLight,
                textColor = Color.White,
                width = 60.dp,
                height = 60.dp,
                padding = 0.dp,
                leadingIcon = painterResource(Res.drawable.icon_support_24px),
                leadingIconModifier = Modifier
                    .width(50.dp)
                    .height(50.dp),
                leadingIconTint = if (state.isReconnecting) IndicatorColor else Color.White,
                onClick = {
                    onAction(ShipermansFriendAction.OnReconnect())
                }
            )
        }
    }
}
