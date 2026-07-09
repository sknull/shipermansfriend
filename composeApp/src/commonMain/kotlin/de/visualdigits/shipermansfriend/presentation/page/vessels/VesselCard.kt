package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.style.gap


@Composable
fun VesselCard(
    state: ShipermansFriendState,
    sizeFactor: Float,
    vessel: AisDataUi,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val isLandscape = state.screenWidth > state.screenHeight
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val cellHeight = 30.dp * sizeFactor

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .dropShadow(
                shape = MaterialTheme.shapes.small,
                shadow = Shadow(
                    radius = 3.dp,
                    spread = 0.dp,
                    color = Color.Black.copy(alpha = 0.5f),
                    offset = DpOffset(3.dp, 3.dp)
                )
            )
    ) {
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
                    sizeFactor = sizeFactor,
                    vessel = vessel
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
                    isHovered = isHovered
                )

                VesselDataFieldsSafety(
                    vessel = vessel,
                    cellHeight = cellHeight
                )
            }
        }
    }
}
