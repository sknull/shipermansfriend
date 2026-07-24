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
import com.cheonjaeung.compose.grid.GridScope
import de.visualdigits.common.presentation.components.util.conditional
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.label_destination
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortRegistry
import de.visualdigits.shipermansfriend.domain.util.capitalizeWords
import de.visualdigits.shipermansfriend.presentation.style.SandYellow
import de.visualdigits.shipermansfriend.presentation.util.routePlatformLink
import org.jetbrains.compose.resources.stringResource

@Composable
fun GridScope.DataFieldDestination(
    vessel: AisDataUi,
    span: Int
) {
    val destinationLocations = if (vessel.destination?.isNotBlank() == true) {
        if (vessel.destination.contains(">")) {
            vessel.destination
                .replace(" ", "")
                .split(">")
                .map { code ->
                    PortRegistry.findPort(code)
                        ?.let { portCode ->
                            Pair("${portCode.name} (${portCode.country})", portCode.location?.googleMapsUrl)
                        }
                        ?: Pair(vessel.destination.capitalizeWords(), null)
                }
        } else {
            PortRegistry.findPort(vessel.destination)
                ?.let { portCode ->
                    listOf(Pair("${portCode.name} (${portCode.country})", portCode.location?.googleMapsUrl))
                }
                ?: listOf(Pair(vessel.destination.capitalizeWords(), null))
        }
    } else {
        listOf()
    }
    val destinationLink = destinationLocations.lastOrNull()?.second
    val destinationValue = destinationLocations.joinToString(" > ") { dl -> dl.first }
    Text(
        modifier = Modifier
            .span { span }
            .conditional(destinationLink?.isNotBlank() == true) { pointerHoverIcon(PointerIcon.Hand) }
            .conditional(destinationLink?.isNotBlank() == true) {
                clickable {
                    destinationLink?.also { dl ->
                        routePlatformLink(
                            dl
                        )
                    }
                }
            },
        text = htmlToAnnotatedString(
            "<b>${stringResource(Res.string.label_destination).uppercase()}</b> ${
                destinationValue.replace(
                    "&",
                    "&nbsp;"
                )
            }"
        ),
        style = MaterialTheme.typography.bodySmall,
        color = if (destinationLocations.isNotEmpty()) if (destinationLink != null) SandYellow else Color.White else Color.Gray
    )
}
