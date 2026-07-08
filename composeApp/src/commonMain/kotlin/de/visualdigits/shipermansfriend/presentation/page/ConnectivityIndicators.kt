package de.visualdigits.shipermansfriend.presentation.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.common.presentation.components.Led
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_business_messages_24px
import de.visualdigits.compose.resources.icon_connectivity_wifi_24px
import de.visualdigits.compose.resources.icon_sailing_24px
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.painterResource


@Composable
fun ConnectivityIndicators(
    modifier: Modifier = Modifier,
    viewModel: ShipermansFriendViewModel,
    sizeFactor: Float,
    iconColor: Color = TextColor
) {
    val connectivityMode by viewModel.connectivityMode.collectAsStateWithLifecycle()
    val aisStreamState by viewModel.aisStreamState.collectAsStateWithLifecycle()
    val receivingDataState by viewModel.receivingDataState.collectAsStateWithLifecycle()

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
    ) {
        Icon(
            modifier = Modifier
                .size(18.dp * sizeFactor),
            painter = painterResource(Res.drawable.icon_connectivity_wifi_24px),
            contentDescription = null,
            tint = iconColor
        )
        Led(
            radius = 8.dp * sizeFactor,
            colorOn = connectivityMode.color,
        )

        Spacer(Modifier.width(MaterialTheme.shapes.gap / 2))

        Icon(
            modifier = Modifier
                .size(18.dp * sizeFactor),
            painter = painterResource(Res.drawable.icon_sailing_24px),
            contentDescription = null,
            tint = iconColor
        )
        Led(
            radius = 8.dp * sizeFactor,
            colorOn = aisStreamState.color,
        )

        Spacer(Modifier.width(MaterialTheme.shapes.gap / 2))

        Icon(
            modifier = Modifier
                .size(18.dp * sizeFactor),
            painter = painterResource(Res.drawable.icon_business_messages_24px),
            contentDescription = null,
            tint = iconColor
        )
        Led(
            radius = 8.dp * sizeFactor,
            colorOn = receivingDataState.color,
        )
    }
}
