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
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.label_callsign
import de.visualdigits.compose.resources.label_imo
import de.visualdigits.compose.resources.label_mmsi
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.style.SandYellow
import de.visualdigits.shipermansfriend.presentation.util.routePlatformLink
import org.jetbrains.compose.resources.stringResource

@Composable
fun DataFieldsVesselIdentification(vessel: AisDataUi) {
    // mmsi
    Text(
        modifier = Modifier
            .pointerHoverIcon(PointerIcon.Hand)
            .clickable {
                routePlatformLink(
                    "https://www.startpage.com/do/dsearch?query=mmsi%20${
                        vessel.mmsi.toString().padStart(9, '0')
                    }"
                )
            },
        text = htmlToAnnotatedString("<b>${stringResource(Res.string.label_mmsi).uppercase()}</b> ${vessel.mmsi}"),
        style = MaterialTheme.typography.bodySmall,
        color = SandYellow
    )

    // callsign
    val callsign = if (vessel.callSign != null) {
        vessel.callSign
    } else {
        ""
    }
    Text(
        modifier = Modifier
            .conditional(callsign.isNotBlank()) { pointerHoverIcon(PointerIcon.Hand) }
            .conditional(callsign.isNotBlank()) { clickable { routePlatformLink("https://www.startpage.com/do/dsearch?query=callsign%20$callsign") } },
        text = htmlToAnnotatedString("<b>${stringResource(Res.string.label_callsign).uppercase()}</b> $callsign"),
        style = MaterialTheme.typography.bodySmall,
        color = if (callsign.isNotBlank()) SandYellow else Color.Gray
    )

    // imo number
    val imo = if (vessel.imoNumber != null) {
        vessel.imoNumber.toString()
    } else {
        ""
    }
    Text(
        modifier = Modifier
            .conditional(imo.isNotBlank()) { pointerHoverIcon(PointerIcon.Hand) }
            .conditional(imo.isNotBlank()) { clickable { routePlatformLink("https://www.startpage.com/do/dsearch?query=imo%20$imo") } },
        text = htmlToAnnotatedString("<b>${stringResource(Res.string.label_imo).uppercase()}</b> $imo"),
        style = MaterialTheme.typography.bodySmall,
        color = if (imo.isNotBlank()) SandYellow else Color.Gray
    )
}
