package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import de.visualdigits.common.domain.util.mix
import de.visualdigits.shipermansfriend.presentation.style.gap

@Composable
fun HoveredVesselBox(
    modifier: Modifier = Modifier,
    activeHoverNameState: MutableState<String?>,
    colorGrid: Color,
    colorBackground: Color
) {
    val currentHoverName = activeHoverNameState.value
    val name = if(currentHoverName?.isNotBlank() == true) currentHoverName else "?"
    if (currentHoverName != null) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .dropShadow(
                        shape = MaterialTheme.shapes.extraSmall,
                        shadow = Shadow(
                            radius = 2.dp,
                            spread = 2.dp,
                            color = Color.Black.copy(alpha = 0.5f),
                            offset = DpOffset(x = 2.dp, y = 2.dp)
                        )
                    ),
            ) {
                Text(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraSmall)
                        .border(2.dp, colorGrid, MaterialTheme.shapes.extraSmall)
                        .background(colorBackground.mix(colorGrid, 0.2f, BlendMode.Multiply))
                        .width(300.dp)
                        .height(IntrinsicSize.Min)
                        .padding(MaterialTheme.shapes.gap),
                    text = name,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = colorGrid,
                    maxLines = 1
                )
            }
        }
    }
}
