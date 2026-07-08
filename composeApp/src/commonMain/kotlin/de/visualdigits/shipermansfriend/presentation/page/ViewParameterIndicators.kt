package de.visualdigits.shipermansfriend.presentation.page

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_directions_boat_24px
import de.visualdigits.compose.resources.icon_move_location_24px
import de.visualdigits.compose.resources.icon_radar_24px
import de.visualdigits.compose.resources.icon_support_24px
import de.visualdigits.shipermansfriend.domain.util.formatDistance
import de.visualdigits.shipermansfriend.domain.util.formatTime
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.painterResource

@Composable
fun ViewParameterIndicators(
    viewModel: ShipermansFriendViewModel,
    state: ShipermansFriendState,
    sizeFactor: Float,
    color: Color = TextColor,
    vesselNumber: Int,
    safetyDeviceNumber: Int,
    currentRadarRadius: Double
) {
    val lastLocationUpdateMinutes by viewModel.lastLocationUpdateDuration.collectAsStateWithLifecycle()

    Row(
        modifier = Modifier
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(18.dp * sizeFactor),
            painter = painterResource(Res.drawable.icon_move_location_24px),
            contentDescription = null,
            tint = color
        )
        Text(
            modifier = Modifier,
            text = lastLocationUpdateMinutes.formatTime(),
            maxLines = 1,
            style = MaterialTheme.typography.titleMedium,
            color = color
        )

        Spacer(Modifier.width(MaterialTheme.shapes.gap / 2))

        Icon(
            modifier = Modifier
                .size(18.dp * sizeFactor),
            painter = painterResource(Res.drawable.icon_directions_boat_24px),
            contentDescription = null,
            tint = color
        )
        Text(
            modifier = Modifier,
            text = vesselNumber.toString(),
            maxLines = 1,
            style = MaterialTheme.typography.titleMedium,
            color = color
        )

        Spacer(Modifier.width(MaterialTheme.shapes.gap / 2))

        Icon(
            modifier = Modifier
                .size(18.dp * sizeFactor),
            painter = painterResource(Res.drawable.icon_radar_24px),
            contentDescription = null,
            tint = color
        )
        Text(
            text = currentRadarRadius.formatDistance(),
            maxLines = 1,
            style = MaterialTheme.typography.titleMedium,
            color = color
        )

        Spacer(Modifier.width(MaterialTheme.shapes.gap / 2))

        Icon(
            modifier = Modifier
                .size(18.dp * sizeFactor),
            painter = painterResource(Res.drawable.icon_support_24px),
            contentDescription = null,
            tint = if (safetyDeviceNumber > 0 && state.hasUnreadSafetyData) Color.Red else color
        )
        Text(
            modifier = Modifier,
            text = safetyDeviceNumber.toString(),
            maxLines = 1,
            style = MaterialTheme.typography.labelMedium,
            color = if (safetyDeviceNumber > 0 && state.hasUnreadSafetyData) Color.Red else color
        )
    }
}
