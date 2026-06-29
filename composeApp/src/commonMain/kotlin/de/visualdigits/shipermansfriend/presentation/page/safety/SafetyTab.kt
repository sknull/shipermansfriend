package de.visualdigits.shipermansfriend.presentation.page.safety

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.common.presentation.components.PlatformVerticalScrollbarBox
import de.visualdigits.common.presentation.components.container.ErrorCard
import de.visualdigits.common.presentation.model.PlatformScrollbarStyle
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.hint_safety_messages
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.page.vessels.VesselCard
import de.visualdigits.shipermansfriend.presentation.style.gap

@Composable
fun SafetyTab(
    viewModel: ShipermansFriendViewModel,
    platformType: PlatformType,
    screenWidth: Dp,
    screenHeight: Dp,
    onAction: (ShipermansFriendAction) -> Unit
) {

    val safetyDevices by viewModel.safetyDevices.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
    ) {
        ErrorCard(
            modifier = Modifier
                .height(100.dp),
            errorMessage = UiText.StringResourceId(Res.string.hint_safety_messages),
            severity = Severity.Info,
            shapeContainer = MaterialTheme.shapes.small
        )

        PlatformVerticalScrollbarBox(
            modifier = Modifier
                .weight(1f)
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
            )
        ) {
            if(safetyDevices.isNotEmpty()) {
                safetyDevices.map { vessel ->
                    Pair("safetyMessage_${vessel.timeUtc}", @Composable {
                        key("safetyMessage_${vessel.timeUtc}") {
                            VesselCard(
                                viewModel = viewModel,
                                screenWidth = screenWidth,
                                screenHeight = screenHeight,
                                vessels = safetyDevices,
                                selectedVessel = vessel,
                                onAction = onAction
                            )
                        }
                    })
                }
            } else {
                listOf()
            }
        }
    }
}
