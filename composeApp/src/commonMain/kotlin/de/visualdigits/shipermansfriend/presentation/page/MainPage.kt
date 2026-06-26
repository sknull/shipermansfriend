package de.visualdigits.shipermansfriend.presentation.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.common.presentation.components.button.IndicatorButton
import de.visualdigits.common.presentation.components.button.TabButtonRow
import de.visualdigits.common.presentation.components.container.ErrorCard
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.tab_categories
import de.visualdigits.compose.resources.tab_driving_vessels
import de.visualdigits.compose.resources.tab_moored_vessels
import de.visualdigits.compose.resources.tab_settings
import de.visualdigits.shipermansfriend.data.repository.AisStreamClient
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.page.categories.CategoriesPage
import de.visualdigits.shipermansfriend.presentation.page.radar.RadarPage
import de.visualdigits.shipermansfriend.presentation.page.settings.SettingsPage
import de.visualdigits.shipermansfriend.presentation.page.vessels.VesselsPage
import de.visualdigits.shipermansfriend.presentation.style.IndicatorColor
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.MyShapes
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import de.visualdigits.shipermansfriend.presentation.style.colorScheme
import de.visualdigits.shipermansfriend.presentation.style.gap
import de.visualdigits.shipermansfriend.presentation.style.typography
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
    viewModel: ShipermansFriendViewModel,
    platformType: PlatformType,
    aisStreamClient: AisStreamClient
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val location by aisStreamClient.location.collectAsStateWithLifecycle()

    val uriHandler = LocalUriHandler.current

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val density = LocalDensity.current
        val screenWidth = maxWidth
        val screenHeight = maxHeight

        val sizeFactor = when {
            screenWidth < 500.dp -> 0.7f
//            screenWidth > 1500.dp -> 1.5f
            else -> 1.0f
        }

        val items = remember {
            linkedMapOf<Pair<String, UiText>, @Composable (() -> Unit)>(
                Pair("driving_vessels", UiText.StringResourceId(Res.string.tab_driving_vessels)) to {
                    VesselsPage(
                        viewModel = viewModel,
                        aisStreamClient = aisStreamClient,
                        platformType = platformType,
                        screenWidth = screenWidth,
                        screenHeight = screenHeight,
                        uriHandler = uriHandler,
                        isMoored = false,
                        onAction = viewModel::onAction,
                        location = { location }
                    )
                },
                Pair("moored_vessels", UiText.StringResourceId(Res.string.tab_moored_vessels)) to {
                    VesselsPage(
                        viewModel = viewModel,
                        aisStreamClient = aisStreamClient,
                        platformType = platformType,
                        screenWidth = screenWidth,
                        screenHeight = screenHeight,
                        uriHandler = uriHandler,
                        location = { location },
                        onAction = viewModel::onAction,
                        isMoored = true
                    )
                },
                Pair("categories", UiText.StringResourceId(Res.string.tab_categories)) to {
                    CategoriesPage(
                        viewModel = viewModel,
                        state = state,
                        aisStreamClient = aisStreamClient,
                        platformType = platformType,
                        screenWidth = screenWidth,
                        screenHeight = screenHeight,
                        uriHandler = uriHandler,
                        onCommonAction = viewModel::onCommonAction,
                        onAction = viewModel::onAction
                    ) { location }
                },
                Pair("settings", UiText.StringResourceId(Res.string.tab_settings)) to {
                    SettingsPage(
                        viewModel = viewModel,
                        onAction = viewModel::onAction
                    )
                },
            )
        }

        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography(
                textColor = TextColor,
                sizeFactor = sizeFactor
            ),
            shapes = MyShapes
        ) {
            LaunchedEffect(Unit) {
                // Umrechnung von Dp in Pixel für Coil
                val wPx = with(density) { screenWidth.roundToPx() }
                val hPx = with(density) { screenHeight.roundToPx() }
                viewModel.onAction(ShipermansFriendAction.UpdateMaxImageSize(state.settings, max(wPx, hPx)))
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .safeDrawingPadding()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(MaterialTheme.shapes.gap),
                ) {
                    ErrorCard(
                        errorMessage = state.uiMessage,
                        severity = state.uiMessageSeverity,
                        shapeContainer = MaterialTheme.shapes.small
                    )

                    if (state.selectedVessel != null) {
                        location?.let { loc ->
                            RadarPage(
                                viewModel = viewModel,
                                state = state,
                                location = loc,
                                isLandscape = screenWidth > screenHeight,
                                onAction = viewModel::onAction
                            )
                        }
                    } else {
                        TabButtonRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .drawBehind {
                                    val strokeWidth = 2.dp.toPx()
                                    drawLine(
                                        color = MarineBlue,
                                        start = Offset(0f, size.height - strokeWidth / 2),
                                        end = Offset(size.width, size.height - strokeWidth / 2),
                                        strokeWidth = strokeWidth
                                    )
                                },
                            horizontalArrangement = Arrangement.spacedBy(0.dp),
                            selectedTab = { state.selectedTabIndex },
                            items = items
                        ) { label, index ->
                            IndicatorButton(
                                buttonColor = MarineBlue,
                                textColor = Color.White,
                                width = screenWidth / 4 - 10.dp,
                                height = 40.dp,
                                text = label.asString(),
                                textStyle = MaterialTheme.typography.titleSmall,
                                indicatorPosition = Alignment.BottomCenter,
                                indicatorColor = IndicatorColor,
                                shape = RoundedCornerShape(
                                    topStart = 6.dp,
                                    topEnd = 6.dp,
                                    bottomStart = 0.dp,
                                    bottomEnd = 0.dp
                                ),
                                selected = state.selectedTabIndex == index,
                                onClick = {
                                    viewModel.onAction(
                                        ShipermansFriendAction.OnTabSelected(index)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
