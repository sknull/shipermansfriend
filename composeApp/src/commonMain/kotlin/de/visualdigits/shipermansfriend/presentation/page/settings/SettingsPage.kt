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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.form.EditableListResources
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.common.presentation.components.container.ErrorCard
import de.visualdigits.common.presentation.components.form.ConfigurationEditForm
import de.visualdigits.common.presentation.components.util.switchBoxColors
import de.visualdigits.common.presentation.model.PlatformScrollbarStyle
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.add
import de.visualdigits.compose.resources.add_hint
import de.visualdigits.compose.resources.cancel
import de.visualdigits.compose.resources.choose_directory
import de.visualdigits.compose.resources.choose_file
import de.visualdigits.compose.resources.delete
import de.visualdigits.compose.resources.edit
import de.visualdigits.compose.resources.icon_add_24px
import de.visualdigits.compose.resources.icon_cancel_24px
import de.visualdigits.compose.resources.icon_check_small_24px
import de.visualdigits.compose.resources.icon_delete_24px
import de.visualdigits.compose.resources.icon_edit_24px
import de.visualdigits.compose.resources.icon_folder_open_24px
import de.visualdigits.compose.resources.icon_visibility_24px
import de.visualdigits.compose.resources.label_service_down
import de.visualdigits.compose.resources.label_service_unknown
import de.visualdigits.compose.resources.label_service_up
import de.visualdigits.compose.resources.ok
import de.visualdigits.compose.resources.title_settings
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun SettingsPage(
    viewModel: ShipermansFriendViewModel,
    onAction: (ShipermansFriendAction) -> Unit
) {

    val serviceState by viewModel.serviceState.collectAsState()
    val editedSettings by viewModel.editedSettings.collectAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        val severity = serviceState?.severity
        ErrorCard(
            severity = severity ?: Severity.Warn,
            errorMessage = when (severity) {
                Severity.Info -> UiText.StringResourceId(Res.string.label_service_up)
                Severity.Error -> UiText.StringResourceId(Res.string.label_service_down)
                else -> UiText.StringResourceId(Res.string.label_service_unknown)
            },
            shapeContainer = MaterialTheme.shapes.small
        )

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
            titleChooseDirectory = UiText.StringResourceId(Res.string.choose_directory),
            titleChooseFile = UiText.StringResourceId(Res.string.choose_file),
            iconFolder = painterResource(Res.drawable.icon_folder_open_24px),
            editableListResources = EditableListResources(
                titleAdd = UiText.StringResourceId(Res.string.add),
                titleEdit = UiText.StringResourceId(Res.string.edit),
                tooltipAdd = UiText.StringResourceId(Res.string.add_hint),
                iconAdd = Res.drawable.icon_add_24px,
                toolTipEdit = UiText.StringResourceId(Res.string.edit),
                iconEdit = Res.drawable.icon_edit_24px,
                toolTipDelete = UiText.StringResourceId(Res.string.delete),
                iconDelete = Res.drawable.icon_delete_24px,
                labelOk = UiText.StringResourceId(Res.string.ok),
                iconOk = Res.drawable.icon_check_small_24px,
                labelCancel = UiText.StringResourceId(Res.string.cancel),
                iconCancel = Res.drawable.icon_cancel_24px
            ),
            tooltipOk = UiText.StringResourceId(Res.string.ok),
            visibilityIcon = painterResource(Res.drawable.icon_visibility_24px),
            iconOk = painterResource(Res.drawable.icon_check_small_24px),
            tooltipCancel = UiText.StringResourceId(Res.string.cancel),
            iconCancel = painterResource(Res.drawable.icon_cancel_24px),
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
            switchColors = switchBoxColors(),
            colorPickerUseOnlySliders = false,
            fieldHeight = 50.dp,
            onValueChange = { keyValue ->
                onAction(
                    ShipermansFriendAction.OnSettingsValueChanged(
                        keyValue = keyValue
                    )
                )
            },
            configuration = editedSettings!!,
            onCancelClick = {
                onAction(
                    ShipermansFriendAction.OnEditSettingsCancelClick()
                )
            },
            onOkClick = {
                onAction(
                    ShipermansFriendAction.OnSaveSettingsClick()
                )
            }
        ) {
            Spacer(Modifier.height(16.dp))

            SettingsMenuBar(onAction = onAction)

            Spacer(Modifier.height(16.dp))
        }
    }
}
