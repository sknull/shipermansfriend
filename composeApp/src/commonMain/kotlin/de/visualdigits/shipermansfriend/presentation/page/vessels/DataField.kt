package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import be.digitalia.compose.htmlconverter.htmlToAnnotatedString
import co.touchlab.kermit.Logger
import de.visualdigits.common.presentation.components.util.conditional
import de.visualdigits.shipermansfriend.domain.model.geodata.DataFieldDescriptor
import de.visualdigits.shipermansfriend.presentation.style.gap
import de.visualdigits.shipermansfriend.presentation.util.routePlatformLink
import org.jetbrains.compose.resources.stringResource

@Composable
fun DataField(
    modifier: Modifier = Modifier,
    dataFieldDescriptor: DataFieldDescriptor
) {
    val value = listOfNotNull(
        dataFieldDescriptor.value.value?.toString(),
        dataFieldDescriptor.value.unit?.let { u -> stringResource(u) }
    ).joinToString(": ")

    val rawtext = "<b>${stringResource(dataFieldDescriptor.label)}</b> ${value.replace("&", "&nbsp;")}"
    Text(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .conditional(dataFieldDescriptor.href == null) { background(dataFieldDescriptor.backgroundColor) }
            .conditional(dataFieldDescriptor.href != null) { background(dataFieldDescriptor.backgroundColorLink) }
            .conditional(dataFieldDescriptor.href != null) { pointerHoverIcon(PointerIcon.Hand) }
            .conditional(dataFieldDescriptor.href != null) { clickable { routePlatformLink(dataFieldDescriptor.href!!) } }
            .padding(MaterialTheme.shapes.gap / 2),
        text = htmlToAnnotatedString(rawtext),
        style = MaterialTheme.typography.bodySmall,
        color = if (dataFieldDescriptor.href == null) dataFieldDescriptor.textColor else dataFieldDescriptor.linkColor
    )
}
