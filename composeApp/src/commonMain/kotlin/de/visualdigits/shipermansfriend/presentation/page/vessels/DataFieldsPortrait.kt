package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import be.digitalia.compose.htmlconverter.htmlToAnnotatedString
import com.cheonjaeung.compose.grid.SimpleGridCells
import com.cheonjaeung.compose.grid.VerticalGrid
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.util.color
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.label_distance
import de.visualdigits.compose.resources.label_last_message
import de.visualdigits.compose.resources.label_message
import de.visualdigits.compose.resources.label_moored
import de.visualdigits.compose.resources.label_speed
import de.visualdigits.compose.resources.label_turnRate
import de.visualdigits.compose.resources.label_unit_kmh
import de.visualdigits.compose.resources.label_unit_knots
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.util.formatDistance
import de.visualdigits.shipermansfriend.domain.util.formatTime
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.stringResource
import kotlin.math.roundToInt
import kotlin.time.Duration

@Composable
fun DataFieldsPortrait(
    vessel: AisDataUi,
    location: Location?,
    currentTime: KmpOffsetDateTime,
    vesselUpdateRate: Duration?
) {
    VerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.shapes.gap / 2),
        columns = SimpleGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
    ) {

        // distance
        Text(
            modifier = Modifier
                .span { 2 },
            text = htmlToAnnotatedString("<b>${stringResource(Res.string.label_distance).uppercase()}</b> ${location?.distanceTo(vessel.extrapolatedPosition(currentTime))?.formatDistance() ?: vessel.distance.formatDistance() }"),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )

        // last message
        Text(
            modifier = Modifier
                .span { 2 },
            text = htmlToAnnotatedString("<b>${stringResource(Res.string.label_last_message).uppercase()}</b> ${currentTime.minus(vessel.timeUtc).formatTime()} ${vesselUpdateRate?.let { vur -> " [${vur.inWholeMinutes}]" } ?: ""}"),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )

        // destination
        DataFieldDestination(vessel, 2)

        // speeds
        if (!vessel.isMoored) {
            Text(
                modifier = Modifier
                    .span { 2 },
                text = htmlToAnnotatedString("<b>${stringResource(Res.string.label_speed).uppercase()}</b> ${vessel.sog} ${stringResource(Res.string.label_unit_knots)} / ${vessel.speedKmh} ${stringResource(Res.string.label_unit_kmh)}"),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
        } else {
            Text(
                modifier = Modifier
                    .span { 2 },
                text = htmlToAnnotatedString("<b>${stringResource(Res.string.label_speed).uppercase()}</b> ${stringResource(Res.string.label_moored)}"),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
        }

        // rate of turn
        val rateOfTurn = if (vessel.rateOfTurnDegreesPerMinute != null) {
            "${vessel.rateOfTurnDegreesPerMinute.roundToInt()} °/min"
        } else {
            ""
        }
        Text(
            text = htmlToAnnotatedString("<b>${stringResource(Res.string.label_turnRate).uppercase()}</b> $rateOfTurn"),
            style = MaterialTheme.typography.bodySmall,
            color = if (rateOfTurn.isNotBlank()) Color.White else Color.Gray
        )

        DataFieldsVesselIdentification(vessel)

        // safety mnessage
        if (vessel.hasSafetyMessage) {
            Text(
                modifier = Modifier
                    .span { 2 },
                text = htmlToAnnotatedString("<b>${stringResource(Res.string.label_message).uppercase()}</b> ${vessel.decodedSafetyMessageText()}"),
                style = MaterialTheme.typography.bodySmall,
                color = vessel.messageSeverity.color()
            )
        }
    }
}
