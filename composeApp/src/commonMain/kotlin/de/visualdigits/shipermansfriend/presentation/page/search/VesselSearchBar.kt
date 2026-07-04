package de.visualdigits.shipermansfriend.presentation.page.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_delete_24px
import de.visualdigits.compose.resources.icon_search_24px
import de.visualdigits.compose.resources.label_search_placeholder
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun VesselSearchBar(
    shape: Shape = MaterialTheme.shapes.small,
    textColor: Color = TextColor,
    iconTint: Color = TextColor,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = TextColor
    ),
    state: ShipermansFriendState,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val textSelectionColors = remember {
        TextSelectionColors(
            handleColor = primaryColor,
            backgroundColor = primaryColor.copy(alpha = 0.4f)
        )
    }

    CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = shape,
            maxLines = 1,
            textStyle = MaterialTheme.typography.bodySmall.copy(color = textColor),
            value = state.vesselSearchText ?: "",
            onValueChange = { text ->
                onAction(ShipermansFriendAction.OnVesselSearchTextChanged(text))
            },
            placeholder = {
                Text(
                    text = stringResource(Res.string.label_search_placeholder),
                    style = MaterialTheme.typography.bodySmall.copy(color = textColor),
                )
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .width(20.dp),
                    painter = painterResource(Res.drawable.icon_search_24px),
                    contentDescription = null,
                    tint = iconTint
                )
            },
            trailingIcon = {
                if (!state.vesselSearchText.isNullOrBlank()) {
                    IconButton(onClick = {
                        // Clear search text on 'X' click
                        onAction(ShipermansFriendAction.OnVesselSearchTextChanged(""))
                    }) {
                        Icon(
                            modifier = Modifier
                                .width(20.dp),
                            painter = painterResource(Res.drawable.icon_delete_24px),
                            contentDescription = "Clear search",
                            tint = iconTint
                        )
                    }
                }
            },
            singleLine = true,
            colors = colors
        )
    }
}
