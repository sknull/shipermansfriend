package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.common.domain.util.copy
import de.visualdigits.common.domain.util.copyFactor
import de.visualdigits.common.presentation.components.Led
import de.visualdigits.common.presentation.components.button.IndicatorButton
import de.visualdigits.common.presentation.components.modifier.angledInnerShadow
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_radar_24px
import de.visualdigits.compose.resources.icon_read_more_24px
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipCategory
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.style.LightGray
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueDark
import de.visualdigits.shipermansfriend.presentation.style.gap
import de.visualdigits.shipermansfriend.presentation.util.routePlatformLink
import org.jetbrains.compose.resources.painterResource

@Composable
fun VesselIconBoxPortrait(
    modifier: Modifier = Modifier,
    viewModel: ShipermansFriendViewModel,
    data: AisDataUi,
    selectedVessel: AisDataUi,
    vessels: List<AisDataUi>,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val locationValue by viewModel.location.collectAsStateWithLifecycle()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val shipType = data.shipType ?: ShipType.Unknown_0

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(MarineBlueDark),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.weight(1f))

            IndicatorButton(
                buttonColor = MarineBlue,
                width = 30.dp,
                height = 30.dp,
                leadingIcon = painterResource(Res.drawable.icon_read_more_24px),
                leadingIconTint = Color.White,
                onClick = {
                    routePlatformLink("https://www.myshiptracking.com/vessels/${selectedVessel.mmsi}-mmsi-${selectedVessel.mmsi}-imo-")
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
                            location = locationValue,
                            vessels = vessels,
                            selectedVessel = selectedVessel
                        )
                    )
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MarineBlue)
                .angledInnerShadow(
                    angle = 135f,
                    distance = 10.dp,
                    alpha = 0.5f,
                    insetSize = 2.dp,
                    insetColorLight = MarineBlue.copyFactor(valueFactor = 1.5f),
                    insetColorShadow = MarineBlue.copyFactor(valueFactor = 0.75f)
                )
                .padding(MaterialTheme.shapes.gap),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
            ) {
                if (shipType.category != ShipCategory.SafetyDevice) {
                    Icon(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        painter = painterResource(shipType.category.icon),
                        contentDescription = shipType.category.name,
                        tint = LightGray,
                    )
                } else {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        painter = painterResource(shipType.category.icon),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                    )
                }

                Text(
                    modifier = Modifier
                        .height(20.dp),
                    text = shipType.category.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = LightGray
                )
            }
        }

        Row(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .background(MarineBlueDark),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Led(
                radius = 10.dp,
                colorOn = if (selectedVessel.hasCriticalSafetyMessage) shipType.category.color.copy(value = 1.0f, saturation = 0.5f) else shipType.category.color
            )
        }
    }
}
