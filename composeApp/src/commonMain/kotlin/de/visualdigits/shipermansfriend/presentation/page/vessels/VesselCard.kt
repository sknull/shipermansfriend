package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.model.VesselsMode
import de.visualdigits.shipermansfriend.presentation.style.gap


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
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val cellHeight = 30.dp * sizeFactor

    BoxWithConstraints(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        val containerWidth = maxWidth
        val rowWidth = if (isLandscape) {
            (maxWidth - MaterialTheme.shapes.gap * 3) / 3
        } else {
            (maxWidth - MaterialTheme.shapes.gap * 3) / 2
        }

        FlowRow (
            modifier = Modifier
                .width(containerWidth)
                .padding(MaterialTheme.shapes.gap / 2),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
        ) {
            VesselNameRow(
                viewModel = viewModel,
                state = state,
                sizeFactor = sizeFactor,
                vessel = vessel,
                location = location,
                vesselsMode = vesselsMode
            )

            VesselButtonRow(
                state = state,
                vessel = vessel,
                onAction = onAction
            )

            VesselDataFieldsStandard(
                rowWidth = rowWidth,
                cellHeight = cellHeight,
                vessel = vessel,
                isHovered = isHovered,
                currentTime = currentTime,
                location = location
            )

            VesselDataFieldsSafety(
                vessel = vessel,
                cellHeight = cellHeight
            )
        }
    }
}
