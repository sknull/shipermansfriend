package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.errorhandling.LogMessage.Companion.log
import de.visualdigits.common.presentation.components.button.IndicatorButton
import de.visualdigits.common.presentation.components.util.conditional
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.label_clear
import de.visualdigits.compose.resources.title_legend
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipCategory
import de.visualdigits.shipermansfriend.domain.model.type.CategoryMode
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction.OnSelectedShipCategory
import de.visualdigits.shipermansfriend.presentation.style.RadarGrid
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

@Composable
fun LegendBox(
    modifier: Modifier = Modifier,
    landscape: Boolean,
    selectedShipCategories: Map<ShipCategory, CategoryMode>,
    onAction: (ShipermansFriendAction) -> Unit
) {
    var categories by remember(ShipCategory.entries) {
        mutableStateOf<List<Pair<ShipCategory, String>>>(emptyList())
    }

    val selectedCategories = selectedShipCategories.keys
    val selectedMode = selectedShipCategories.values.firstOrNull() ?: CategoryMode.unselected

    log(Severity.Info, "LegendBox - selectedCategories: $selectedCategories", withTag = "AIS")
    log(Severity.Info, "LegendBox - selectedMode: $selectedMode", withTag = "AIS")

    LaunchedEffect(ShipCategory.entries) {
        val lookupMap = ShipCategory.entries
            .associateWith { category -> getString(category.label) }
        categories = ShipCategory.entries.mapNotNull { c ->
            lookupMap[c]?.let { label -> Pair(c, label) }
        }.sortedBy { (_, label) -> label }
    }


    Column(
        modifier = modifier
            .border(1.dp, RadarGrid)
            .padding(MaterialTheme.shapes.gap),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.title_legend),
            style = MaterialTheme.typography.titleSmall,
            color = RadarGrid
        )

        FlowRow (
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
        ) {
            categories.forEach { (category, label) ->
                Row(
                    modifier = Modifier
                        .conditional(landscape) { fillMaxWidth() },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    // category indicator
                    Row(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.extraSmall)
                            .conditional(landscape) { weight(1f) }
                            .height(15.dp)
                            .background(Color(0xFF444444))
                    ) {
                        Box(
                            modifier = Modifier
                                .conditional(landscape) { width(10.dp) }
                                .conditional(!landscape) { width(5.dp) }
                                .height(15.dp)
                                .background(category.color)
                        )

                        Text(
                            modifier = Modifier
                                .width(IntrinsicSize.Max)
                                .padding(horizontal = MaterialTheme.shapes.gap, vertical = MaterialTheme.shapes.gap / 2),
                            text = label,
                            maxLines = 1,
                            softWrap = false,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                            color = Color.White
                        )
                    }

                    // solo
                    IndicatorButton(
                        buttonColor = if (selectedCategories.contains(category) && selectedMode == CategoryMode.solo) Color.Yellow else Color(0xFF333333),
                        text = "S",
                        textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 8.sp),
                        textColor = if (selectedCategories.contains(category) && selectedMode == CategoryMode.solo) TextColor else Color.White,
                        padding = 0.dp,
                        width = 15.dp,
                        height = 15.dp,
                        onClick = {
                            if (!selectedShipCategories.contains(category) || selectedShipCategories[category] == CategoryMode.mute) {
                                onAction(OnSelectedShipCategory(category = category, mode = CategoryMode.solo))
                            } else {
                                onAction(OnSelectedShipCategory(category = category, mode = CategoryMode.unselected))
                            }
                        }
                    )

                    // mute
                    IndicatorButton(
                        buttonColor = if (selectedCategories.contains(category) && selectedMode == CategoryMode.mute) Color.Red else Color(0xFF333333),
                        text = "M",
                        textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 8.sp),
                        textColor = Color.White,
                        padding = 0.dp,
                        width = 15.dp,
                        height = 15.dp,
                        onClick = {
                            if (!selectedShipCategories.contains(category) || selectedShipCategories[category] == CategoryMode.solo) {
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
                    .conditional(landscape) { fillMaxWidth() }
            ) {
                IndicatorButton(
                    textModifier = Modifier
                        .conditional(landscape) { fillMaxWidth() }
                        .conditional(!landscape) { width(IntrinsicSize.Max) },
                    padding = MaterialTheme.shapes.gap / 2,
                    buttonColor = Color.White,
                    text = stringResource(Res.string.label_clear),
                    textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                    maxLines = 1,
                    width = Dp.Unspecified,
                    height = 15.dp,
                    onClick = {
                        onAction(ShipermansFriendAction.OnClearShipCategories())
                    }
                )
            }
        }
    }
}

