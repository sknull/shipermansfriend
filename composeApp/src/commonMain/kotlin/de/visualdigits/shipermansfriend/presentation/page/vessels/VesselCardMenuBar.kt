package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.common.presentation.components.button.IndicatorButton
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.Shipermans_Banner
import de.visualdigits.compose.resources.icon_radar_24px
import de.visualdigits.compose.resources.icon_read_more_24px
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.gap
import de.visualdigits.shipermansfriend.presentation.util.routePlatformLink
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun VesselCardMenuBar(
    viewModel: ShipermansFriendViewModel,
    modifier: Modifier = Modifier,
    selectedVessel: AisDataUi,
    onAction: (ShipermansFriendAction) -> Unit,
    vessels: List<AisDataUi>,
    showVesselName: Boolean,
    size: Dp = 50.dp
) {
    val locationValue by viewModel.location.collectAsStateWithLifecycle()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(size),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showVesselName) {
            Image(
                modifier = Modifier
                    .width(30.dp),
                painter = painterResource(selectedVessel.mmsiCountryPrefix.country.flag),
                contentDescription = null,
                contentScale = ContentScale.Fit,
            )

            Text(
                text = "[${selectedVessel.mmsiCountryPrefix.country.countryName}] ${selectedVessel.safetyNote?.let {sn -> stringResource((sn))}?:selectedVessel.name}",
                style = MaterialTheme.typography.labelMedium
            )
        }

        Spacer(Modifier.weight(1f))

        IndicatorButton(
            buttonColor = MarineBlue,
            width = size,
            height = size,
            leadingIcon = painterResource(Res.drawable.icon_read_more_24px),
            leadingIconTint = Color.White,
            onClick = {
                routePlatformLink("https://www.myshiptracking.com/vessels/${selectedVessel.mmsi}-mmsi-${selectedVessel.mmsi}-imo-")
            }
        )

        IndicatorButton(
            buttonColor = MarineBlue,
            width = size,
            height = size,
            leadingIcon = painterResource(Res.drawable.icon_radar_24px),
            leadingIconTint = Color.White,
            onClick = {
                onAction(
                    ShipermansFriendAction.OnShowRadar(
                        location = locationValue,
                        vessels = vessels,
                        selectedVessel = selectedVessel
                    )
                )
            }
        )
    }
}
