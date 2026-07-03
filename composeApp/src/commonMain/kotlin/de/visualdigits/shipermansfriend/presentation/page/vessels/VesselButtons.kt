package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.util.copy
import de.visualdigits.common.presentation.components.Led
import de.visualdigits.common.presentation.components.button.IndicatorButton
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_add_a_photo_24px
import de.visualdigits.compose.resources.icon_radar_24px
import de.visualdigits.compose.resources.icon_read_more_24px
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.style.IndicatorColor
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueDark
import de.visualdigits.shipermansfriend.presentation.style.gap
import de.visualdigits.shipermansfriend.presentation.util.routePlatformLink
import org.jetbrains.compose.resources.painterResource

@Composable
fun VesselButtons(
    state: ShipermansFriendState,
    location: Location?,
    selectedVessel: AisDataUi,
    vessels: List<AisDataUi>,
    buttonSize: Dp,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val vesselInProtocol = state.photoProtocol.containsKey(selectedVessel.mmsi)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(MarineBlueDark),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val shipType = selectedVessel.shipType ?: ShipType.Unknown_0

        Spacer(Modifier.width(5.dp))

        Led(
            radius = 8.dp,
            colorOn = if (selectedVessel.hasCriticalSafetyMessage) shipType.category.color.copy(value = 1.0f, saturation = 0.5f) else shipType.category.color
        )

        Spacer(Modifier.weight(1f))

        IndicatorButton(
            buttonColor = MarineBlue,
            width = buttonSize,
            height = buttonSize,
            leadingIcon = painterResource(Res.drawable.icon_add_a_photo_24px),
            leadingIconTint = if (vesselInProtocol) IndicatorColor else Color.White,
            onClick = {
                onAction(ShipermansFriendAction.OnAddVesselToPhotoProtocol(selectedVessel))
            }
        )

        IndicatorButton(
            buttonColor = MarineBlue,
            width = buttonSize,
            height = buttonSize,
            leadingIcon = painterResource(Res.drawable.icon_read_more_24px),
            leadingIconTint = Color.White,
            onClick = {
                routePlatformLink("https://www.myshiptracking.com/vessels/${selectedVessel.mmsi}-mmsi-${selectedVessel.mmsi}-imo-")
            }
        )

        IndicatorButton(
            buttonColor = MarineBlue,
            width = buttonSize,
            height = buttonSize,
            leadingIcon = painterResource(Res.drawable.icon_radar_24px),
            leadingIconTint = Color.White,
            onClick = {
                onAction(
                    ShipermansFriendAction.OnShowRadar(
                        location = location,
                        vessels = vessels,
                        selectedVessel = selectedVessel
                    )
                )
            }
        )
    }
}
