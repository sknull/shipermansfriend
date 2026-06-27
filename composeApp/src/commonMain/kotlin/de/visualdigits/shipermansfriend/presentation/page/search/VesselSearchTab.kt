package de.visualdigits.shipermansfriend.presentation.page.search
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.presentation.components.PlatformVerticalScrollbarBox
import de.visualdigits.common.presentation.model.CommonAction
import de.visualdigits.common.presentation.model.PlatformScrollbarStyle
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_delete_24px
import de.visualdigits.compose.resources.icon_search_24px
import de.visualdigits.compose.resources.label_search_placeholder
import de.visualdigits.compose.resources.warning_no_results
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.page.vessels.VesselCard
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun VesselSearchTab(
    viewModel: ShipermansFriendViewModel,
    state: ShipermansFriendState,
    screenWidth: Dp,
    screenHeight: Dp,
    uriHandler: UriHandler,
    platformType: PlatformType,
    location: () -> Location?,
    onAction: (ShipermansFriendAction) -> Unit,
    onCommonAction: (CommonAction) -> Unit
) {
    val locationValue = location()
    val vessels by viewModel.searchedVessels.collectAsState()
    val primaryColor = MaterialTheme.colorScheme.primary
    val textSelectionColors = remember {
        TextSelectionColors(
            handleColor = primaryColor,
            backgroundColor = primaryColor.copy(alpha = 0.4f)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
    ) {
        // 1. STATIK SEARCH FIELD (Always visible, no overlay dropdown)
        CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {
            OutlinedTextField(
                value = state.vesselSearchText ?: "",
                onValueChange = { text ->
                    onAction(ShipermansFriendAction.OnVesselSearchTextChanged(text))
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(Res.string.label_search_placeholder)) },
                leadingIcon = { Icon(painterResource(Res.drawable.icon_search_24px), contentDescription = null) },
                trailingIcon = {
                    if (!state.vesselSearchText.isNullOrBlank()) {
                        IconButton(onClick = {
                            // Clear search text on 'X' click
                            onAction(ShipermansFriendAction.OnVesselSearchTextChanged(""))
                        }) {
                            Icon(painterResource(Res.drawable.icon_delete_24px), contentDescription = "Clear search")
                        }
                    }
                },
                singleLine = true,
                shape = MaterialTheme.shapes.small
            )
        }

        // 2. RESULTS CONTAINER
        if (vessels.isNotEmpty()) {
            PlatformVerticalScrollbarBox(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = if (platformType == PlatformType.jvm) 20.dp else 0.dp),
                scrollbarModifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .width(10.dp)
                    .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)),
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
                vessels.map { vessel ->
                    Pair("searchVessel_${vessel.mmsi}", @Composable {
                        key("searchVessel_${vessel.mmsi}") {
                            VesselCard(
                                uriHandler = uriHandler,
                                screenWidth = screenWidth,
                                screenHeight = screenHeight,
                                location = locationValue,
                                vessels = vessels,
                                selectedVessel = vessel,
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
