package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.style.gap


@Composable
fun VesselCard(
    viewModel: ShipermansFriendViewModel,
    screenWidth: Dp,
    screenHeight: Dp,
    sizeFactor: Float,
    vessels: List<AisDataUi>,
    selectedVessel: AisDataUi,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val locationValue by viewModel.location.collectAsStateWithLifecycle()

    val isLandscape = screenWidth > screenHeight
    val iconWidth = if(isLandscape) screenWidth / 5 else screenWidth / 3
    val cardHeight = if (isLandscape) {
        if (selectedVessel.hasSafetyMessage) {
            260.dp
        } else {
            200.dp
        }
    } else {
        if (selectedVessel.hasSafetyMessage) {
            430.dp
        } else {
            360.dp
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .dropShadow(
                shape = RoundedCornerShape(8.dp),
                shadow = Shadow(
                    radius = 4.dp,
                    spread = 2.dp,
                    color = Color.Black.copy(alpha = 0.5f),
                    offset = DpOffset((5).dp, 5.dp)
                )
            )
    ) {
        Row(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                val cellWidth = if(isLandscape) {
                    (maxWidth - MaterialTheme.shapes.gap * 2) / 3
                } else {
                    maxWidth
                }

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(MaterialTheme.shapes.gap / 2),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
                ) {
                    VesselNameRow(
                        sizeFactor = sizeFactor,
                        selectedVessel = selectedVessel,
                        isLandscape = isLandscape,
                        onAction = onAction,
                        location = locationValue,
                        vessels = vessels
                    )

                    if (isLandscape) {
                        DataFieldsLandscape(
                            cellWidth = cellWidth,
                            vessel = selectedVessel
                        )
                    } else {
                        DataFieldsPortrait(
                            cellWidth = cellWidth,
                            vessel = selectedVessel
                        )
                    }
                }
            }

            if (isLandscape) {
                VesselIconBoxLandscape(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(iconWidth),
                    selectedVessel = selectedVessel
                )
            } else {
                VesselIconBoxPortrait(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(iconWidth),
                    viewModel = viewModel,
                    data = selectedVessel,
                    selectedVessel = selectedVessel,
                    vessels = vessels,
                    onAction = onAction
                )
            }
        }
    }
}
