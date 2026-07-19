package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import be.digitalia.compose.htmlconverter.htmlToAnnotatedString
import de.visualdigits.common.presentation.components.util.conditional
import de.visualdigits.shipermansfriend.domain.model.geodata.DataFieldDescriptor
import de.visualdigits.shipermansfriend.presentation.style.SandYellow
import de.visualdigits.shipermansfriend.presentation.util.routePlatformLink
import org.jetbrains.compose.resources.stringResource

@Composable
fun DataField(
    modifier: Modifier = Modifier,
    descriptor: DataFieldDescriptor
) {
    val value = listOfNotNull(
        descriptor.value.value?.toString(),
        descriptor.value.unit?.let { u -> stringResource(u) }
    ).joinToString(": ")

    val rawtext = "<b>${stringResource(descriptor.label).uppercase()}</b> ${value.replace("&", "&nbsp;")}"
    Text(
        modifier = modifier
            .conditional(descriptor.href != null) { pointerHoverIcon(PointerIcon.Hand) }
            .conditional(descriptor.href != null) { clickable { routePlatformLink(descriptor.href!!) } },
        text = htmlToAnnotatedString(rawtext),
        style = MaterialTheme.typography.bodySmall.copy(fontSize = MaterialTheme.typography.bodySmall.fontSize * descriptor.sizeFactor),
        color = if (descriptor.href == null) Color.White else SandYellow
    )
}
