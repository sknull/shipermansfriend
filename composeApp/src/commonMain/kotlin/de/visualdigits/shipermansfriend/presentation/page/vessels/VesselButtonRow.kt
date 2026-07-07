package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.visualdigits.common.presentation.components.button.IndicatorButton
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_add_a_photo_24px
import de.visualdigits.compose.resources.icon_radar_24px
import de.visualdigits.compose.resources.icon_read_more_24px
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.style.IndicatorColor
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.gap
import de.visualdigits.shipermansfriend.presentation.util.routePlatformLink
import org.jetbrains.compose.resources.painterResource

@Composable
fun VesselButtonRow(
    state: ShipermansFriendState,
    vessel: AisDataUi,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val vesselInProtocol = state.photoProtocol.containsKey(vessel.mmsi)

    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .fillMaxWidth()
            .height(30.dp),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IndicatorButton(
            buttonColor = MarineBlue,
            width = 30.dp,
            height = 30.dp,
            leadingIcon = painterResource(Res.drawable.icon_add_a_photo_24px),
            leadingIconTint = if (vesselInProtocol) IndicatorColor else Color.White,
            onClick = {
                onAction(ShipermansFriendAction.OnAddVesselToPhotoProtocol(vessel))
            }
        )

        IndicatorButton(
            buttonColor = MarineBlue,
            width = 30.dp,
            height = 30.dp,
            leadingIcon = painterResource(Res.drawable.icon_read_more_24px),
            leadingIconTint = Color.White,
            onClick = {
                routePlatformLink("https://www.myshiptracking.com/vessels/${vessel.mmsi}-mmsi-${vessel.mmsi}-imo-")
            }
        )

        IndicatorButton(
            buttonColor = MarineBlue,
            width = 30.dp,
            height = 30.dp,
            leadingIcon = painterResource(Res.drawable.icon_radar_24px),
            leadingIconTint = Color.White,
            onClick = {
                onAction(
                    ShipermansFriendAction.OnShowRadar(
                        selectedVessel = vessel
                    )
                )
            }
        )
    }
}
