package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import de.visualdigits.common.presentation.components.Led
import de.visualdigits.common.presentation.components.button.IndicatorButton
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_business_messages_24px
import de.visualdigits.compose.resources.icon_connectivity_wifi_24px
import de.visualdigits.compose.resources.icon_directions_boat_24px
import de.visualdigits.compose.resources.icon_move_location_24px
import de.visualdigits.compose.resources.icon_my_location_24px
import de.visualdigits.compose.resources.icon_radar_24px
import de.visualdigits.compose.resources.icon_sailing_24px
import de.visualdigits.compose.resources.icon_support_24px
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.AisStreamState
import de.visualdigits.shipermansfriend.domain.util.formatDistance
import de.visualdigits.shipermansfriend.domain.util.formatTime
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.style.IndicatorColor
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.TextColor
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
    val lastLocationUpdateMinutes by viewModel.lastLocationUpdateDuration.collectAsStateWithLifecycle()
    val locationValue by viewModel.location.collectAsStateWithLifecycle()

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
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(MaterialTheme.shapes.gap),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
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
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
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
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .width(24.dp * sizeFactor),
                            painter = painterResource(Res.drawable.icon_move_location_24px),
                            contentDescription = null,
                            tint = TextColor
                        )
                        Text(
                            modifier = Modifier,
                            text = lastLocationUpdateMinutes.formatTime(),
                            maxLines = 1,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.width(MaterialTheme.shapes.gap / 2))

                        Icon(
                            modifier = Modifier
                                .width(24.dp * sizeFactor),
                            painter = painterResource(Res.drawable.icon_directions_boat_24px),
                            contentDescription = null,
                            tint = TextColor
                        )
                        Text(
                            modifier = Modifier,
                            text = vesselNumber.toString(),
                            maxLines = 1,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.width(MaterialTheme.shapes.gap / 2))

                        Icon(
                            modifier = Modifier
                                .width(24.dp * sizeFactor),
                            painter = painterResource(Res.drawable.icon_radar_24px),
                            contentDescription = null,
                            tint = TextColor
                        )
                        Text(
                            text = currentRadarRadius.formatDistance(),
                            maxLines = 1,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .width(14.dp),
                            painter = painterResource(Res.drawable.icon_connectivity_wifi_24px),
                            contentDescription = null,
                            tint = TextColor
                        )
                        Led(
                            radius = 5.dp,
                            colorOn = connectivityMode.color,
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .width(14.dp),
                            painter = painterResource(Res.drawable.icon_sailing_24px),
                            contentDescription = null,
                            tint = TextColor
                        )
                        Led(
                            radius = 5.dp,
                            colorOn = aisStreamState.color,
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .width(14.dp),
                            painter = painterResource(Res.drawable.icon_business_messages_24px),
                            contentDescription = null,
                            tint = TextColor
                        )
                        Led(
                            radius = 5.dp,
                            colorOn = receivingDataState.color,
                        )
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
}
