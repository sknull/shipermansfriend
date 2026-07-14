package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.cheonjaeung.compose.grid.ExperimentalGridApi
import com.cheonjaeung.compose.grid.SimpleGridCells
import com.cheonjaeung.compose.grid.VerticalGrid
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.presentation.components.button.IndicatorButton
import de.visualdigits.common.presentation.components.util.conditional
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_bookmark_24px
import de.visualdigits.compose.resources.icon_bookmark_added_24px
import de.visualdigits.compose.resources.icon_my_location_24px
import de.visualdigits.compose.resources.icon_radar_24px
import de.visualdigits.compose.resources.icon_read_more_24px
import de.visualdigits.compose.resources.icon_warning_24px
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.model.VesselsMode
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueEvenLighter
import de.visualdigits.shipermansfriend.presentation.style.RedAlert
import de.visualdigits.shipermansfriend.presentation.style.gap
import de.visualdigits.shipermansfriend.presentation.util.routePlatformLink
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalGridApi::class)
@Composable
fun VesselCard(
    viewModel: ShipermansFriendViewModel,
    state: ShipermansFriendState,
    sizeFactor: Float,
    vessel: AisDataUi,
    currentTime: KmpOffsetDateTime,
    location: Location?,
    vesselsMode: VesselsMode,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val isLandscape = state.screenWidth > state.screenHeight
    val columns = if (isLandscape) 3 else 2
    val vesselStarred = state.starredVessels.containsKey(vessel.mmsi)
    val vesselAlerted = state.alertVessels.contains(vessel.mmsi)

    VerticalGrid(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(MaterialTheme.shapes.gap / 2),
        columns = SimpleGridCells.Fixed(columns),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
    ) {
        VesselNameRow(
            modifier = Modifier.span { columns },
            viewModel = viewModel,
            state = state,
            vessel = vessel,
            location = location,
            vesselsMode = vesselsMode
        )

        // flag and country
        Row(
            modifier = Modifier
                .span { columns }
                .clip(MaterialTheme.shapes.extraSmall)
                .fillMaxWidth()
                .background(MarineBlueEvenLighter)
                .padding(MaterialTheme.shapes.gap / 2),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .height(25.dp * sizeFactor),
                painter = painterResource(vessel.mmsiCountryPrefix.country.flag),
                contentDescription = vessel.mmsiCountryPrefix.country.countryName,
                contentScale = ContentScale.Fit,
            )

            Text(
                text = vessel.mmsiCountryPrefix.country.countryName,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // buttons
        Row(
            modifier = Modifier
                .span { columns }
                .clip(MaterialTheme.shapes.extraSmall)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                leadingIcon = painterResource(Res.drawable.icon_my_location_24px),
                leadingIconTint = Color.White,
                onClick = {
                    routePlatformLink("https://www.google.com/maps/search/?api=1&query=${vessel.location.latitude}%2C${vessel.location.longitude}")
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

            Spacer(Modifier.weight(1f))

            IndicatorButton(
                buttonColor = MarineBlue,
                width = 30.dp,
                height = 30.dp,
                leadingIcon = if (vesselStarred) painterResource(Res.drawable.icon_bookmark_added_24px) else painterResource(Res.drawable.icon_bookmark_24px),
                leadingIconTint = Color.White,
                onClick = {
                    onAction(ShipermansFriendAction.OnToggleStarredVessel(vessel))
                }
            )
            IndicatorButton(
                buttonColor = MarineBlue,
                width = 30.dp,
                height = 30.dp,
                leadingIcon = painterResource(Res.drawable.icon_warning_24px),
                leadingIconTint = if (vesselAlerted) RedAlert else Color.White,
                onClick = {
                    onAction(ShipermansFriendAction.OnToggleVesselAlert(vessel))
                }
            )
        }

        vessel
            .toDataFields(location, currentTime)
            .forEach {  descriptor ->
                DataField(
                    modifier = Modifier
                        .conditional(descriptor.wholeRow) { span { columns } },
                    dataFieldDescriptor = descriptor
                )
            }
    }
}
