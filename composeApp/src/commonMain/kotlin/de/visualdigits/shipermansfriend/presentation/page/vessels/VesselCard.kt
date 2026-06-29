package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import de.visualdigits.common.presentation.components.util.conditional
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.stringResource


@Composable
fun VesselCard(
    viewModel: ShipermansFriendViewModel,
    screenWidth: Dp,
    screenHeight: Dp,
    vessels: List<AisDataUi>,
    selectedVessel: AisDataUi,
    simple: Boolean = false,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val isLandscape = screenWidth > screenHeight
    val iconWidth = if(isLandscape) screenWidth / 5 else screenWidth / 3
    val cellWidth = if(isLandscape) {
        if (simple) {
            (screenWidth - MaterialTheme.shapes.gap) / 3
        } else {
            (screenWidth - iconWidth - MaterialTheme.shapes.gap) / 3
        }
    } else {
        if (simple) {
            screenWidth - MaterialTheme.shapes.gap
        } else {
            screenWidth - iconWidth - MaterialTheme.shapes.gap
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .conditional(!simple) {
                dropShadow(
                    shape = RoundedCornerShape(8.dp),
                    shadow = Shadow(
                        radius = 4.dp,
                        spread = 2.dp,
                        color = Color.Black.copy(alpha = 0.5f),
                        offset = DpOffset((5).dp, 5.dp)
                    )
                )
            }
    ) {
        Row(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(MaterialTheme.shapes.gap / 2),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
            ) {
                if (isLandscape) {
                    VesselCardMenuBar(
                        viewModel = viewModel,
                        selectedVessel = selectedVessel,
                        onAction = onAction,
                        vessels = vessels,
                        showVesselName = true
                    )
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedVessel.safetyNote?.let {sn -> stringResource((sn))}?:selectedVessel.name,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }

                if (isLandscape) {
                    DataFieldsLandscape(
                        cellWidth = cellWidth,
                        vessel = selectedVessel
                    )
                } else {
                    DataFieldsPortrait(
                        vessel = selectedVessel
                    )
                }
            }

            if (!simple) {
                if (isLandscape) {
                    VesselIconBoxLandscape(
                        iconWidth = iconWidth,
                        data = selectedVessel
                    )
                } else {
                    VesselIconBoxPortrait(
                        viewModel = viewModel,
                        iconWidth = iconWidth,
                        data = selectedVessel,
                        selectedVessel = selectedVessel,
                        vessels = vessels,
                        onAction = onAction
                    )
                }
            }
        }
    }
}
