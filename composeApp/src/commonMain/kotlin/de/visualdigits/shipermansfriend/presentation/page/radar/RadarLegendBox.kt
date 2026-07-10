package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import de.visualdigits.common.presentation.components.button.IndicatorButton
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.label_clear
import de.visualdigits.compose.resources.title_categories
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipCategory
import de.visualdigits.shipermansfriend.domain.model.type.CategoryMode
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction.OnSelectedShipCategory
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.style.RadarBackground
import de.visualdigits.shipermansfriend.presentation.style.RadarGrid
import de.visualdigits.shipermansfriend.presentation.style.RedAlert
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

@Composable
fun RadarLegendBox(
    sizeFactor: Float,
    state: ShipermansFriendState,
    onAction: (ShipermansFriendAction) -> Unit
) {
    var categories by remember(ShipCategory.entries) {
        mutableStateOf<List<Pair<ShipCategory, String>>>(emptyList())
    }

    val selectedCategories = state.selectedShipCategories.keys
    val selectedMode = state.selectedShipCategories.values.firstOrNull() ?: CategoryMode.unselected

    LaunchedEffect(ShipCategory.entries) {
        val lookupMap = ShipCategory.entries
            .associateWith { category -> getString(category.label) }
        categories = ShipCategory.entries.mapNotNull { c ->
            lookupMap[c]?.let { label -> Pair(c, label) }
        }.sortedBy { (_, label) -> label }
    }

    val buttonSize = 30.dp * sizeFactor
    val buttonTextSize = 20.sp * sizeFactor
    val muteButtonTextSize = 15.sp * sizeFactor

    BoxWithConstraints(
        modifier = Modifier
                .fillMaxWidth(),
        contentAlignment = Alignment.TopEnd
    ) {
        val rowWidth = min((maxWidth - MaterialTheme.shapes.gap * 3) / 2, 300.dp * sizeFactor)
        val containerWidth = rowWidth * 2 + MaterialTheme.shapes.gap * 3

        FlowRow (
            modifier = Modifier
                .width(containerWidth)
                .border(1.dp, RadarGrid)
                .background(RadarBackground)
                .padding(MaterialTheme.shapes.gap / 2),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(Res.string.title_categories),
                    style = MaterialTheme.typography.titleSmall,
                    color = RadarGrid,
                    textAlign = TextAlign.Center
                )
            }
            categories.forEach { (category, label) ->
                Row(
                    modifier = Modifier
                        .width(rowWidth),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    // category indicator
                    Row(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.extraSmall)
                            .weight(1f)
                            .height(buttonSize - 1.dp) // why?
                            .background(Color(0xFF444444)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(10.dp)
                                .fillMaxHeight()
                                .background(category.color)
                        )

                        Text(
                            modifier = Modifier
                                .width(IntrinsicSize.Max)
                                .padding(horizontal = MaterialTheme.shapes.gap, vertical = 0.dp),
                            text = label,
                            maxLines = 1,
                            softWrap = false,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = buttonTextSize),
                            color = Color.White
                        )
                    }

                    // solo
                    IndicatorButton(
                        buttonColor = if (selectedCategories.contains(category) && selectedMode == CategoryMode.solo) Color.Yellow else Color(0xFF333333),
                        text = "S",
                        textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = muteButtonTextSize),
                        textColor = if (selectedCategories.contains(category) && selectedMode == CategoryMode.solo) TextColor else Color.White,
                        padding = 0.dp,
                        width = buttonSize,
                        height = buttonSize,
                        onClick = {
                            if (!state.selectedShipCategories.contains(category) || state.selectedShipCategories[category] == CategoryMode.mute) {
                                onAction(OnSelectedShipCategory(category = category, mode = CategoryMode.solo))
                            } else {
                                onAction(OnSelectedShipCategory(category = category, mode = CategoryMode.unselected))
                            }
                        }
                    )

                    // mute
                    IndicatorButton(
                        buttonColor = if (selectedCategories.contains(category) && selectedMode == CategoryMode.mute) RedAlert else Color(0xFF333333),
                        text = "M",
                        textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = muteButtonTextSize),
                        textColor = Color.White,
                        padding = 0.dp,
                        width = buttonSize,
                        height = buttonSize,
                        onClick = {
                            if (!state.selectedShipCategories.contains(category) || state.selectedShipCategories[category] == CategoryMode.solo) {
                                onAction(OnSelectedShipCategory(category = category, mode = CategoryMode.mute))
                            } else {
                                onAction(OnSelectedShipCategory(category = category, mode = CategoryMode.unselected))
                            }
                        }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(buttonSize)
            ) {
                IndicatorButton(
                    textModifier = Modifier
                        .fillMaxWidth(),
                    padding = MaterialTheme.shapes.gap / 2,
                    buttonColor = Color.White,
                    text = stringResource(Res.string.label_clear),
                    textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = buttonTextSize),
                    maxLines = 1,
                    width = Dp.Unspecified,
                    height = buttonSize,
                    onClick = {
                        onAction(ShipermansFriendAction.OnClearShipCategories())
                    }
                )
            }
        }
    }
}

