package de.visualdigits.shipermansfriend.presentation.page.search
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.presentation.components.PlatformVerticalScrollbarBox
import de.visualdigits.common.presentation.model.CommonAction
import de.visualdigits.common.presentation.model.PlatformScrollbarStyle
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.warning_no_results
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.page.vessels.VesselCard
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.stringResource

@Composable
fun VesselSearchTab(
    viewModel: ShipermansFriendViewModel,
    state: ShipermansFriendState,
    sizeFactor: Float,
    platformType: PlatformType,
    onAction: (ShipermansFriendAction) -> Unit,
    onCommonAction: (CommonAction) -> Unit
) {
    val searchedVessels by viewModel.searchedVessels.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MaterialTheme.shapes.gap),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
    ) {
        VesselSearchBar(
            modifier = Modifier
                .height(30.dp)
                .padding(0.dp),
            state = state,
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
            onAction = onAction
        )

        if (searchedVessels.isNotEmpty()) {
            PlatformVerticalScrollbarBox(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = if (platformType == PlatformType.jvm) 20.dp else 0.dp),
                scrollbarModifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .width(10.dp)
                    .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)),
                platformType = platformType,
                scrollbarStyle = PlatformScrollbarStyle(
                    minimalHeight = 16.dp,
                    thickness = 8.dp,
                    shape = RoundedCornerShape(4.dp),
                    hoverDurationMillis = 300,
                    unhoverColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                    hoverColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                ),
                scrollbarId = "search",
                scrollPosition = viewModel.scrollPosition,
                onCommonAction = onCommonAction
            ) {
                searchedVessels.map { vessel ->
                    Pair("searchVessel_${vessel.mmsi}", @Composable {
                        key("searchVessel_${vessel.mmsi}") {
                            VesselCard(
                                state = state,
                                sizeFactor = sizeFactor,
                                vessel = vessel,
                                onAction = onAction
                            )
                        }
                    })
                }
            }
        } else {
            // 3. EMPTY STATE FALLBACK
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.warning_no_results),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}
