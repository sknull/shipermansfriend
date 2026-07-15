package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.presentation.components.PlatformVerticalScrollbarBox
import de.visualdigits.common.presentation.components.container.VerticalCollapsibleBoxSimple
import de.visualdigits.common.presentation.model.CommonAction
import de.visualdigits.common.presentation.model.PlatformScrollbarStyle
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_arrow_drop_down_24px
import de.visualdigits.compose.resources.icon_arrow_right_24px
import de.visualdigits.shipermansfriend.di.AnthemStorage
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.MovementDirection
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.model.VesselsMode
import de.visualdigits.shipermansfriend.presentation.page.search.VesselSearchBar
import de.visualdigits.shipermansfriend.presentation.style.CollapsibleBox
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import de.visualdigits.shipermansfriend.presentation.style.gap
import eu.iamkonstantin.kotlin.gadulka.GadulkaPlayer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun VesselsDynamic(
    vesselsMode: VesselsMode,
    state: ShipermansFriendState,
    onAction: (ShipermansFriendAction) -> Unit,
    viewModel: ShipermansFriendViewModel,
    sizeFactor: Float,
    vessels: Map<MovementDirection, List<AisDataUi>>,
    vesselsStarred: Map<Long, AisDataUi>,
    platformType: PlatformType,
    onCommonAction: (CommonAction) -> Unit,
    currentTime: KmpOffsetDateTime,
    player: GadulkaPlayer,
    anthemStorage: AnthemStorage,
    location: Location
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MaterialTheme.shapes.gap),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
    ) {
        if (vesselsMode == VesselsMode.SEARCH) {
            VesselSearchBar(
                modifier = Modifier
                    .height(30.dp)
                    .padding(0.dp),
                state = state,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                onAction = onAction
            )
        } else {
            LocationBar(
                viewModel = viewModel,
                state = state,
                sizeFactor = sizeFactor,
                vesselNumber = vessels.values.sumOf { l -> l.size },
                onAction = viewModel::onAction
            )
        }

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
                hoverColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            ),
            scrollbarId = "vessels_driving",
            scrollPosition = viewModel.scrollPosition,
            onCommonAction = onCommonAction
        ) {
            if (vessels.isNotEmpty()) {
                MovementDirection.entries
                    .filter { d -> vessels.containsKey(d) }
                    .map { direction ->
                        Pair("vessels_${vesselsMode.name}_${direction.name}", @Composable {
                            VerticalCollapsibleBoxSimple(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(2.dp, CollapsibleBox, MaterialTheme.shapes.small),
                                backgroundColor = Color.Transparent,
                                paddingContainer = PaddingValues(
                                    start = MaterialTheme.shapes.gap,
                                    end = MaterialTheme.shapes.gap * 2, // need some space for the shadow
                                    top = MaterialTheme.shapes.gap,
                                    bottom = MaterialTheme.shapes.gap * 2, // need some space for the shadow
                                ),
                                isTitleHoverable = true,
                                titleHoverColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                modifierHeader = Modifier
                                    .background(CollapsibleBox),
                                titleContent = {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(MaterialTheme.shapes.gap),
                                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            modifier = Modifier
                                                .height(20.dp),
                                            painter = painterResource(direction.icon),
                                            contentDescription = null,
                                            tint = TextColor
                                        )
                                        Text(
                                            text = stringResource(direction.label),
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                },
                                shape = MaterialTheme.shapes.small,
                                onStateChange = { state ->
                                    onAction(
                                        ShipermansFriendAction.OnCollapsibleStateChange(
                                            "vessels_${vesselsMode.name}_${direction.name}",
                                            state
                                        )
                                    )
                                },
                                isExpanded = state.collapsibleState["vessels_${vesselsMode.name}_${direction.name}"] == true,
                                iconArrowRight = painterResource(Res.drawable.icon_arrow_right_24px),
                                iconArrowDown = painterResource(Res.drawable.icon_arrow_drop_down_24px),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
                                ) {
                                    vessels[direction]?.forEach { vessel ->
                                        key("vessel_${vesselsMode.name}_${direction.name}_${vessel.mmsi}") {
                                            VesselCard(
                                                viewModel = viewModel,
                                                state = state,
                                                sizeFactor = sizeFactor,
                                                vessel = vessel,
                                                vesselsStarred = vesselsStarred,
                                                currentTime = currentTime,
                                                location = location,
                                                player = player,
                                                anthemStorage = anthemStorage,
                                                onAction = onAction
                                            )
                                        }
                                    }
                                }
                            }
                        })
                    }
            } else listOf()
        }
    }
}
