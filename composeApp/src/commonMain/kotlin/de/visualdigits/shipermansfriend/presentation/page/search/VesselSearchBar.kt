package de.visualdigits.shipermansfriend.presentation.page.search

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.VisualTransformation
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VesselSearchBar(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
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
        BasicTextField(
            value = state.vesselSearchText ?: "",
            onValueChange = { text ->
                onAction(ShipermansFriendAction.OnVesselSearchTextChanged(text))
            },
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
            textStyle = MaterialTheme.typography.bodySmall.copy(color = textColor),
            maxLines = 1,
            singleLine = true,
            decorationBox = @Composable { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    value = state.vesselSearchText ?: "",
                    innerTextField = innerTextField,
                    enabled = true,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = remember { MutableInteractionSource() },
                    contentPadding = contentPadding,
                    placeholder = {
                        Text(
                            text = stringResource(Res.string.label_search_placeholder),
                            style = MaterialTheme.typography.bodySmall.copy(color = textColor),
                        )
                    },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.width(20.dp),
                            painter = painterResource(Res.drawable.icon_search_24px),
                            contentDescription = null,
                            tint = iconTint
                        )
                    },
                    trailingIcon = {
                        if (!state.vesselSearchText.isNullOrBlank()) {
                            IconButton(onClick = {
                                onAction(ShipermansFriendAction.OnVesselSearchTextChanged(""))
                            }) {
                                Icon(
                                    modifier = Modifier.width(20.dp),
                                    painter = painterResource(Res.drawable.icon_delete_24px),
                                    contentDescription = "Clear search",
                                    tint = iconTint
                                )
                            }
                        }
                    },
                    colors = colors,
                    container = {
                        OutlinedTextFieldDefaults.Container(
                            enabled = true,
                            isError = false,
                            interactionSource = remember { MutableInteractionSource() },
                            colors = colors,
                            shape = shape,
                            focusedBorderThickness = 2.dp,
                            unfocusedBorderThickness = 1.dp,
                        )
                    }
                )
            }
        )
    }
}
