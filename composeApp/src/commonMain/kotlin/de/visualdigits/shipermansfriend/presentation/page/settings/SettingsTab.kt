package de.visualdigits.shipermansfriend.presentation.page.settings

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.domain.model.ui.UiPlatform
import de.visualdigits.common.presentation.components.androidPlatform
import de.visualdigits.common.presentation.components.form.ConfigurationEditForm
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.title_settings
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import org.jetbrains.compose.resources.stringResource


@Composable
fun SettingsTab(
    viewModel: ShipermansFriendViewModel,
    platformType: PlatformType,
    onAction: (ShipermansFriendAction) -> Unit
) {

    val editedSettings by viewModel.editedSettings.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = stringResource(Res.string.title_settings),
                style = MaterialTheme.typography.headlineMedium
            )
        }

        ConfigurationEditForm(
            platformType = platformType,
            configuration = editedSettings!!,
            scrollbarModifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .width(10.dp)
                .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)),
            onValueChange = { keyValue ->
                onAction(
                    ShipermansFriendAction.OnSettingsValueChanged(
                        keyValue = keyValue
                    )
                )
            },
            onCancelClick = {
                onAction(
                    ShipermansFriendAction.OnEditSettingsCancelClick()
                )
            },
            onOkClick = {
                onAction(
                    ShipermansFriendAction.OnSaveSettingsClick()
                )
            },
            headerContent = {
                if (androidPlatform() != UiPlatform.UI_MODE_TYPE_TELEVISION) {
                    Spacer(Modifier.height(16.dp))

                    SettingsMenuBar(onAction = onAction)

                    Spacer(Modifier.height(16.dp))
                }
            }
        )
    }
}
