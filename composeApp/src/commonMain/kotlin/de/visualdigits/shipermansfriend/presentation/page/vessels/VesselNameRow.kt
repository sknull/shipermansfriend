package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.presentation.components.button.IndicatorButton
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_radar_24px
import de.visualdigits.compose.resources.icon_read_more_24px
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueEvenLighter
import de.visualdigits.shipermansfriend.presentation.style.gap
import de.visualdigits.shipermansfriend.presentation.util.routePlatformLink
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun VesselNameRow(
    sizeFactor: Float,
    selectedVessel: AisDataUi,
    isLandscape: Boolean,
    onAction: (ShipermansFriendAction) -> Unit,
    location: Location?,
    vessels: List<AisDataUi>
) {
    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .fillMaxWidth()
            .height(50.dp * sizeFactor)
            .background(MarineBlueEvenLighter)
            .padding(horizontal = MaterialTheme.shapes.gap, vertical = MaterialTheme.shapes.gap / 2),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            modifier = Modifier
                .width(30.dp)
                .padding(top = 2.dp),
            painter = painterResource(selectedVessel.mmsiCountryPrefix.country.flag),
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            val vesselName = selectedVessel.safetyNote?.let { sn -> stringResource((sn)) }
                ?: selectedVessel.name
            if (vesselName.isNotBlank()) {
                Text(
                    text = vesselName,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Text(
                text = selectedVessel.mmsiCountryPrefix.country.countryName,
                style = MaterialTheme.typography.bodySmall
            )
        }

        if (isLandscape) {
            Spacer(Modifier.weight(1f))

            IndicatorButton(
                buttonColor = MarineBlue,
                width = 50.dp,
                height = 50.dp,
                leadingIcon = painterResource(Res.drawable.icon_read_more_24px),
                leadingIconTint = Color.White,
                onClick = {
                    routePlatformLink("https://www.myshiptracking.com/vessels/${selectedVessel.mmsi}-mmsi-${selectedVessel.mmsi}-imo-")
                }
            )

            IndicatorButton(
                buttonColor = MarineBlue,
                width = 50.dp,
                height = 50.dp,
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
}
