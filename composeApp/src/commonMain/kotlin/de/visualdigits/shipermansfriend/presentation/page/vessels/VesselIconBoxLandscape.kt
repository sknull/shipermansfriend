package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import de.visualdigits.common.domain.util.copy
import de.visualdigits.common.domain.util.copyFactor
import de.visualdigits.common.presentation.components.Led
import de.visualdigits.common.presentation.components.modifier.angledInnerShadow
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipCategory
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType
import de.visualdigits.shipermansfriend.presentation.style.LightGray
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueDark
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.painterResource

@Composable
fun VesselIconBoxLandscape(
    modifier: Modifier = Modifier,
    selectedVessel: AisDataUi
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val shipType = selectedVessel.shipType ?: ShipType.Unknown_0

        Column(
            modifier = Modifier
                .width(40.dp)
                .fillMaxHeight()
                .background(MarineBlueDark),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Led(
                radius = 10.dp,
                colorOn = if (selectedVessel.hasCriticalSafetyMessage) shipType.category.color.copy(value = 1.0f, saturation = 0.5f) else shipType.category.color
            )
        }

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
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
            ) {
                if (shipType.category != ShipCategory.SafetyDevice) {
                    Icon(
                        modifier = Modifier
                            .weight(1f),
                        painter = painterResource(shipType.category.icon),
                        contentDescription = shipType.category.name,
                        tint = LightGray,
                    )
                } else {
                    Image(
                        modifier = Modifier
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
    }
}
