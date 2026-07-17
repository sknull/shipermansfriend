package de.visualdigits.shipermansfriend.presentation.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.common.presentation.components.BindBackHandler
import de.visualdigits.common.presentation.components.button.IndicatorButton
import de.visualdigits.common.presentation.components.button.TabButtonRow
import de.visualdigits.common.presentation.components.container.ErrorCard
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_anchor_24px
import de.visualdigits.compose.resources.icon_bookmark_24px
import de.visualdigits.compose.resources.icon_health_and_safety_24px
import de.visualdigits.compose.resources.icon_info_24px
import de.visualdigits.compose.resources.icon_search_24px
import de.visualdigits.compose.resources.icon_settings_24px
import de.visualdigits.compose.resources.icon_warning_24px
import de.visualdigits.compose.resources.vessel_Pilot
import de.visualdigits.shipermansfriend.di.AudioStorage
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.page.radar.RadarPage
import de.visualdigits.shipermansfriend.presentation.page.safety.VesselsTabSafety
import de.visualdigits.shipermansfriend.presentation.page.search.VesselsTabSearch
import de.visualdigits.shipermansfriend.presentation.page.settings.SettingsTab
import de.visualdigits.shipermansfriend.presentation.page.vessels.VesselsTabAlerted
import de.visualdigits.shipermansfriend.presentation.page.vessels.VesselsTabDriving
import de.visualdigits.shipermansfriend.presentation.page.vessels.VesselsTabMoored
import de.visualdigits.shipermansfriend.presentation.page.vessels.VesselsTabStarred
import de.visualdigits.shipermansfriend.presentation.style.IndicatorColor
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.MyShapes
import de.visualdigits.shipermansfriend.presentation.style.RedAlert
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import de.visualdigits.shipermansfriend.presentation.style.colorScheme
import de.visualdigits.shipermansfriend.presentation.style.gap
import de.visualdigits.shipermansfriend.presentation.style.typography
import eu.iamkonstantin.kotlin.gadulka.GadulkaPlayer
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
    viewModel: ShipermansFriendViewModel,
    platformType: PlatformType,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val location by viewModel.location.collectAsStateWithLifecycle()
    val vesselsAlertedGrouped by viewModel.vesselsAlertedGrouped.collectAsStateWithLifecycle()
    val vesselsStarred = viewModel.vesselsStarred.collectAsStateWithLifecycle()
    val vesselsWarned = viewModel.vesselsWarned.collectAsStateWithLifecycle()
    val vesselsInInnerRadius = viewModel.vesselsInInnerRadius.collectAsStateWithLifecycle()
    val player = koinInject<GadulkaPlayer>()
    val audioStorage = koinInject<AudioStorage>()

    BindBackHandler(isEnabled = state.previousSelectedTabIndexes.isNotEmpty()) {
        viewModel.onAction(ShipermansFriendAction.OnBackButton())
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        var screenWidth by remember { mutableStateOf(maxWidth) }
        LaunchedEffect(maxWidth, maxHeight) {
            viewModel.onAction(ShipermansFriendAction.OnReportScreenSize(maxWidth, maxHeight))
            screenWidth = maxWidth
        }

        val sizeFactor = when {
            maxWidth < 500.dp -> 0.7f
//            screenWidth > 1500.dp -> 1.5f
            else -> 1.0f
        }

        val items = remember {
            linkedMapOf<Triple<String, (@Composable () -> Unit)?, UiText>, @Composable () -> Unit>(
                Triple(
                    "driving_vessels",
                    @Composable {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(24.dp),
                                painter = painterResource(Res.drawable.vessel_Pilot),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    },
                    UiText.DynamicString("")
                ) to {
                    location?.let { loc ->
                        VesselsTabDriving(
                            viewModel = viewModel,
                            state = state,
                            vesselsStarred = vesselsStarred.value,
                            vesselsWarned = vesselsWarned.value,
                            vesselsInInnerRadius = vesselsInInnerRadius.value,
                            alertVessels = state.alertVessels,
                            sizeFactor = sizeFactor,
                            platformType = platformType,
                            location = loc,
                            player = player,
                            audioStorage = audioStorage,
                            onCommonAction = viewModel::onCommonAction,
                            onAction = viewModel::onAction
                        )
                    }
                },
                Triple(
                    "moored_vessels",
                    @Composable {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(24.dp),
                                painter = painterResource(Res.drawable.icon_anchor_24px),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    },
                    UiText.DynamicString("")
                ) to {
                    location?.let { loc ->
                        VesselsTabMoored(
                            viewModel = viewModel,
                            state = state,
                            vesselsStarred = vesselsStarred.value,
                            vesselsWarned = vesselsWarned.value,
                            vesselsInInnerRadius = vesselsInInnerRadius.value,
                            alertVessels = state.alertVessels,
                            sizeFactor = sizeFactor,
                            platformType = platformType,
                            location = loc,
                            player = player,
                            audioStorage = audioStorage,
                            onCommonAction = viewModel::onCommonAction,
                            onAction = viewModel::onAction
                        )
                    }
                },
                Triple(
                    "starred_vessels",
                    @Composable {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(24.dp),
                                painter = painterResource(Res.drawable.icon_bookmark_24px),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    },
                    UiText.DynamicString("")
                ) to {
                    location?.let { loc ->
                        VesselsTabStarred(
                            viewModel = viewModel,
                            state = state,
                            vesselsStarred = vesselsStarred.value,
                            vesselsWarned = vesselsWarned.value,
                            vesselsInInnerRadius = vesselsInInnerRadius.value,
                            alertVessels = state.alertVessels,
                            sizeFactor = sizeFactor,
                            platformType = platformType,
                            location = loc,
                            player = player,
                            audioStorage = audioStorage,
                            onCommonAction = viewModel::onCommonAction,
                            onAction = viewModel::onAction
                        )
                    }
                },
                Triple(
                    "alerted_vessels",
                    @Composable {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(24.dp),
                                painter = painterResource(Res.drawable.icon_warning_24px),
                                contentDescription = null,
                                tint = if (vesselsAlertedGrouped.isNotEmpty()) RedAlert else Color.White
                            )
                        }
                    },
                    UiText.DynamicString("")
                ) to {
                    location?.let { loc ->
                        VesselsTabAlerted(
                            viewModel = viewModel,
                            state = state,
                            vesselsStarred = vesselsStarred.value,
                            vesselsWarned = vesselsWarned.value,
                            vesselsInInnerRadius = vesselsInInnerRadius.value,
                            alertVessels = state.alertVessels,
                            sizeFactor = sizeFactor,
                            platformType = platformType,
                            location = loc,
                            vessels = vesselsAlertedGrouped,
                            player = player,
                            audioStorage = audioStorage,
                            onCommonAction = viewModel::onCommonAction,
                            onAction = viewModel::onAction
                        )
                    }
                },
                Triple(
                    "safety",
                    @Composable {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.icon_health_and_safety_24px),
                                contentDescription = null,
                                tint = if (state.hasUnreadSafetyData) RedAlert else Color.White
                            )
                        }
                    },
                    UiText.DynamicString("")
                ) to {
                    location?.let { loc ->
                        VesselsTabSafety(
                            viewModel = viewModel,
                            state = state,
                            vesselsStarred = vesselsStarred.value,
                            vesselsWarned = vesselsWarned.value,
                            vesselsAlerted = vesselsInInnerRadius.value,
                            alertVessels = state.alertVessels,
                            sizeFactor = sizeFactor,
                            platformType = platformType,
                            location = loc,
                            player = player,
                            audioStorage = audioStorage,
                            onCommonAction = viewModel::onCommonAction,
                            onAction = viewModel::onAction,
                        )
                    }
                },
                Triple(
                    "search",
                    @Composable {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.icon_search_24px),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    },
                    UiText.DynamicString("")
                ) to {
                    location?.let { loc ->
                        VesselsTabSearch(
                            viewModel = viewModel,
                            state = state,
                            vesselsStarred = vesselsStarred.value,
                            vesselsWarned = vesselsWarned.value,
                            vesselsAlerted = vesselsInInnerRadius.value,
                            alertVessels = state.alertVessels,
                            sizeFactor = sizeFactor,
                            platformType = platformType,
                            location = loc,
                            onCommonAction = viewModel::onCommonAction,
                            player = player,
                            audioStorage = audioStorage,
                            onAction = viewModel::onAction
                        )
                    }
                },
                Triple(
                    "settings",
                    @Composable {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.icon_settings_24px),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    },
                    UiText.DynamicString("")
                ) to {
                    SettingsTab(
                        viewModel = viewModel,
                        platformType = platformType,
                        onAction = viewModel::onAction
                    )
                },
                Triple(
                    "info",
                    @Composable {
                        Icon(
                            painter = painterResource(Res.drawable.icon_info_24px),
                            contentDescription = null,
                            tint = Color.White
                        )
                    },
                    UiText.DynamicString("")
                ) to {
                    InfoTab(
                        state = state,
                        platformType = platformType,
                        player = player,
                        audioStorage = audioStorage,
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .safeDrawingPadding()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    ErrorCard(
                        errorMessage = state.uiMessage,
                        severity = state.uiMessageSeverity,
                        shapeContainer = MaterialTheme.shapes.small
                    )

                    if (state.isShowingRadar) {
                        location?.let { loc ->
                            RadarPage(
                                viewModel = viewModel,
                                state = state,
                                sizeFactor = sizeFactor,
                                location = loc,
                                onAction = viewModel::onAction
                            )
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(MaterialTheme.shapes.gap)
                        ) {
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
                                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
                                selectedTab = { state.selectedTabIndex },
                                items = items
                            ) { content, label, index ->
                                IndicatorButton(
                                    modifier = Modifier
                                        .width(min(50.dp, (screenWidth - MaterialTheme.shapes.gap / 2 * (items.size + 1)) / items.size)),
                                    buttonColor = MarineBlue,
                                    textColor = Color.White,
                                    width = Dp.Unspecified,
                                    height = 40.dp,
                                    content = content,
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
}
