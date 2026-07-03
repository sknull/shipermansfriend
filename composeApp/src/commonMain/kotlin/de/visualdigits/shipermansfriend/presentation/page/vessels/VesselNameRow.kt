package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueEvenLighter
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun VesselNameRow(
    modifier: Modifier = Modifier,
    selectedVessel: AisDataUi
) {
    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .fillMaxWidth()
            .height(40.dp)
            .background(MarineBlueEvenLighter)
            .padding(horizontal = MaterialTheme.shapes.gap, vertical = MaterialTheme.shapes.gap / 2),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            modifier = Modifier
                .width(30.dp)
                .padding(top = 2.dp),
            painter = painterResource(selectedVessel.mmsiCountryPrefix.country.flag),
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            val vesselName = selectedVessel.safetyNote?.let { sn -> stringResource((sn)) }
                ?: selectedVessel.name
            if (vesselName.isNotBlank()) {
                Text(
                    text = vesselName,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Text(
                text = selectedVessel.mmsiCountryPrefix.country.countryName,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
