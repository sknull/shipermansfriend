package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import de.visualdigits.compose.resources.icon_direction_24px
import de.visualdigits.compose.resources.icon_my_location_24px
import de.visualdigits.compose.resources.icon_play_arrow_24px
import de.visualdigits.compose.resources.icon_radar_24px
import de.visualdigits.compose.resources.icon_read_more_24px
import de.visualdigits.compose.resources.icon_stop_24px
import de.visualdigits.compose.resources.icon_warning_24px
import de.visualdigits.shipermansfriend.di.AudioStorage
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipCategory
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.Country
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueLight
import de.visualdigits.shipermansfriend.presentation.style.RedAlert
import de.visualdigits.shipermansfriend.presentation.style.YellowAlert
import de.visualdigits.shipermansfriend.presentation.style.gap
import de.visualdigits.shipermansfriend.presentation.util.routePlatformLink
import eu.iamkonstantin.kotlin.gadulka.GadulkaPlayer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalGridApi::class)
@Composable
fun VesselCard(
    state: ShipermansFriendState,
    sizeFactor: Float,
    vessel: AisDataUi,
    vesselStarred: Boolean,
    vesselWarned: Boolean,
    vesselInInnerRadius: Boolean,
    vesselInAlertList: Boolean,
    currentTime: KmpOffsetDateTime,
    location: Location?,
    player: GadulkaPlayer,
    audioStorage: AudioStorage,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val isLandscape = state.screenWidth > state.screenHeight
    val columns = if (isLandscape) 4 else 2
    val countryCode = vessel.mmsiCountryPrefix.country.countryCode
    val country = Country.fromPrefix(countryCode)
    var audioUri by remember(countryCode) { mutableStateOf<String?>(null) }
    LaunchedEffect(countryCode) {
        audioUri = audioStorage.prepareAudio(country?.anthemFile)
    }
    val dataFields = vessel
        .toDataFields(location, currentTime).values

    val buttonSize = 30.dp
    val buttonColor = MarineBlueLight
    val buttonShape = RoundedCornerShape(2.dp)
    val buttonPadding = 2.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            // flag, name and indicators
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(
                        topStart = 4.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    ))
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(MarineBlue)
                    .padding(MaterialTheme.shapes.gap),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .width(30.dp * sizeFactor),
                        painter = painterResource(vessel.mmsiCountryPrefix.country.flag),
                        contentDescription = vessel.mmsiCountryPrefix.country.countryName,
                        contentScale = ContentScale.Fit,
                    )

                    if (vessel.name.isNotBlank()) {
                        Text(
                            modifier = Modifier
                                .padding(MaterialTheme.shapes.gap / 2),
                            text = vessel.name.uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }

                    Spacer(Modifier.weight(1f))

                    Icon(
                        modifier = Modifier
                            .width(30.dp * sizeFactor)
                            .rotate(vessel.heading.toFloat())
                            .padding(MaterialTheme.shapes.gap / 2),
                        painter = painterResource(Res.drawable.icon_direction_24px),
                        contentDescription = null,
                        tint = Color.White
                    )

                    Icon(
                        modifier = Modifier
                            .width(30.dp * sizeFactor)
                            .padding(MaterialTheme.shapes.gap / 2),
                        painter = painterResource(vessel.movementDirection.icon),
                        contentDescription = null,
                        tint = if (vesselWarned) {
                            YellowAlert
                        } else if (vesselInInnerRadius) {
                            RedAlert
                        } else {
                            Color.White
                        }
                    )
                }

                // country name
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text =  vessel.mmsiCountryPrefix.country.countryName,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }

                // buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IndicatorButton(
                        buttonColor = buttonColor,
                        shape = buttonShape,
                        width = buttonSize,
                        height = buttonSize,
                        padding = buttonPadding,
                        leadingIcon = painterResource(Res.drawable.icon_read_more_24px),
                        leadingIconTint = Color.White,
                        onClick = {
                            routePlatformLink("https://www.myshiptracking.com/vessels/${vessel.mmsi}-mmsi-${vessel.mmsi}-imo-")
                        }
                    )

                    IndicatorButton(
                        buttonColor = buttonColor,
                        shape = buttonShape,
                        width = buttonSize,
                        height = buttonSize,
                        padding = buttonPadding,
                        leadingIcon = painterResource(Res.drawable.icon_my_location_24px),
                        leadingIconTint = Color.White,
                        onClick = {
                            routePlatformLink("https://www.google.com/maps/search/?api=1&query=${vessel.location.latitude}%2C${vessel.location.longitude}")
                        }
                    )

                    IndicatorButton(
                        buttonColor = buttonColor,
                        shape = buttonShape,
                        width = buttonSize,
                        height = buttonSize,
                        padding = buttonPadding,
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

                    audioUri?.let { a ->
                        IndicatorButton(
                            buttonColor = buttonColor,
                            shape = buttonShape,
                            width = buttonSize,
                            height = buttonSize,
                            padding = buttonPadding,
                            leadingIcon = if (state.playingAnthem == countryCode) painterResource(Res.drawable.icon_stop_24px) else  painterResource(Res.drawable.icon_play_arrow_24px),
                            leadingIconTint = Color.White,
                            onClick = {
                                if (state.playingAnthem != countryCode) {
                                    onAction(ShipermansFriendAction.OnPlayAnthem(countryCode))
                                    player.play(a)
                                } else {
                                    onAction(ShipermansFriendAction.OnPlayAnthem(null))
                                    player.stop()
                                }
                            }
                        )
                    }

                    Spacer(Modifier.weight(1f))

                    IndicatorButton(
                        buttonColor = buttonColor,
                        shape = buttonShape,
                        width = buttonSize,
                        height = buttonSize,
                        padding = buttonPadding,
                        leadingIcon = if (vesselStarred) painterResource(Res.drawable.icon_bookmark_added_24px) else painterResource(Res.drawable.icon_bookmark_24px),
                        leadingIconTint = Color.White,
                        onClick = {
                            onAction(ShipermansFriendAction.OnToggleStarredVessel(vessel))
                        }
                    )
                    IndicatorButton(
                        buttonColor = buttonColor,
                        shape = buttonShape,
                        width = buttonSize,
                        height = buttonSize,
                        padding = buttonPadding,
                        leadingIcon = painterResource(Res.drawable.icon_warning_24px),
                        leadingIconTint = if (vesselInAlertList) RedAlert else Color.White,
                        onClick = {
                            onAction(ShipermansFriendAction.OnToggleVesselAlert(vessel))
                        }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 4.dp,
                        bottomEnd = 0.dp
                    ))
                    .fillMaxWidth()
                    .weight(1f)
                    .background(MarineBlue)
                    .padding(MaterialTheme.shapes.gap),
            ) {
                VerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.shapes.gap / 2),
                    columns = SimpleGridCells.Fixed(columns),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
                ) {
                    dataFields
                        .forEach {  descriptor ->
                            DataField(
                                modifier = Modifier
                                    .conditional(descriptor.wholeRow) { span { columns } },
                                descriptor = descriptor
                            )
                        }
                }
            }
        }

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 4.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 4.dp
                ))
                .width(80.dp)
                .fillMaxHeight()
                .background(MarineBlue)
                .padding(MaterialTheme.shapes.gap),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp),
                painter = painterResource(vessel.shipType.category.icon),
                contentDescription = vessel.shipType.category.name,
                contentScale = ContentScale.Fit,
                colorFilter = if (vessel.shipType.category != ShipCategory.SafetyDevice) {
                    ColorFilter.tint(Color.White)
                } else {
                    null
                }
            )

            Text(
                text =  stringResource(vessel.shipType.category.label),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )

            Text(
                text =   "${vessel.totalLength} x ${vessel.totalWidth} m",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = MaterialTheme.typography.bodySmall.fontSize * 0.75),
                color = Color.White
            )
        }
    }
}
