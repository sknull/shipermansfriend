package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipCategory
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueDark
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.getString

@Composable
fun LegendBox(
) {
    var categories by remember(ShipCategory.entries) {
        mutableStateOf<List<Pair<ShipCategory, String>>>(emptyList())
    }

    LaunchedEffect(ShipCategory.entries) {
        val lookupMap = ShipCategory.entries
            .associateWith { category -> getString(category.label) }
        categories = ShipCategory.entries.mapNotNull { c ->
            lookupMap[c]?.let { label -> Pair(c, label) }
        }.sortedBy { (_, label) -> label }
    }

    FlowRow (
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
    ) {
        categories
            .forEach { (category, label) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
            ) {
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraSmall)
                        .border(1.dp, MarineBlueDark, MaterialTheme.shapes.extraSmall)
                        .width(20.dp)
                        .height(10.dp)
                        .background(category.color)
                )

                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
