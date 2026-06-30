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
import de.visualdigits.common.presentation.components.Led
import de.visualdigits.common.presentation.components.button.IndicatorButton
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_directions_boat_24px
import de.visualdigits.compose.resources.icon_move_location_24px
import de.visualdigits.compose.resources.icon_my_location_24px
import de.visualdigits.compose.resources.icon_radar_24px
import de.visualdigits.compose.resources.icon_support_24px
import de.visualdigits.compose.resources.label_minutes
import de.visualdigits.shipermansfriend.domain.model.geodata.ReceiverState
import de.visualdigits.shipermansfriend.domain.util.formatDistance
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import de.visualdigits.shipermansfriend.presentation.style.gap
import de.visualdigits.shipermansfriend.presentation.util.routePlatformLink
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun LocationBox(
    viewModel: ShipermansFriendViewModel,
    receiverState: ReceiverState,
    lastLocationUpdate: Long,
    currentRadarRadius: Double,
    vesselNumber: Int,
    onAction: (ShipermansFriendAction) -> Unit
) {
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
                .height(80.dp)
                .background(MaterialTheme.colorScheme.surface)
                .padding(MaterialTheme.shapes.gap),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                verticalAlignment = Alignment.CenterVertically
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

                        Led(
                            radius = 10.dp,
                            colorOn = receiverState.color,
                            isOn = receiverState != ReceiverState.noData
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.icon_move_location_24px),
                            contentDescription = null,
                            tint = TextColor
                        )
                        Text(
                            modifier = Modifier,
                            text = "$lastLocationUpdate ${stringResource(Res.string.label_minutes)}",
                            maxLines = 1,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.width(MaterialTheme.shapes.gap / 2))

                        Icon(
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
                            painter = painterResource(Res.drawable.icon_radar_24px),
                            contentDescription = null,
                            tint = TextColor
                        )
                        Text(
                            modifier = Modifier,
                            text = currentRadarRadius.formatDistance(),
                            maxLines = 1,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                if (receiverState.ordinal > ReceiverState.connectionLost.ordinal && receiverState != ReceiverState.serverDown) {
                    IndicatorButton(
                        buttonColor = MarineBlue,
                        textColor = Color.White,
                        width = 50.dp,
                        height = 50.dp,
                        leadingIcon = painterResource(Res.drawable.icon_support_24px),
                        leadingIconTint = Color.White,
                        onClick = {
                            onAction(ShipermansFriendAction.OnReconnect())
                        }
                    )
                }
            }
        }
    }
}
