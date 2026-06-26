package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.util.copyFactor
import de.visualdigits.common.presentation.components.Led
import de.visualdigits.common.presentation.components.modifier.angledInnerShadow
import de.visualdigits.common.presentation.components.util.conditional
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.style.LightGray
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueDark
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.painterResource

@Composable
fun VesselIconBoxPortrait(
    iconWidth: Dp,
    data: AisDataUi,
    cardHeight: Dp,
    location: Location?,
    selectedVessel: AisDataUi,
    vessels: List<AisDataUi>,
    uriHandler: UriHandler,
    onAction: (ShipermansFriendAction) -> Unit
) {
    Column(
        modifier = Modifier
            .width(iconWidth)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val shipType = data.shipType ?: ShipType.Unknown_0

        VesselCardMenuBar(
            modifier = Modifier
                .background(MarineBlueDark),
            selectedVessel = selectedVessel,
            uriHandler = uriHandler,
            onAction = onAction,
            location = location,
            vessels = vessels,
            showVesselName = false,
            size = 30.dp
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
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
                    .height(IntrinsicSize.Min),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
            ) {
                Icon(
                    modifier = Modifier
                        .conditional(iconWidth < cardHeight) { width(iconWidth - 10.dp) }
                        .conditional(iconWidth >= cardHeight) { height(cardHeight - 50.dp) },
                    painter = painterResource(shipType.category.icon),
                    contentDescription = shipType.category.name,
                    tint = LightGray,
                )

                Text(
                    modifier = Modifier,
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
                colorOn = shipType.category.color
            )
        }
    }
}
