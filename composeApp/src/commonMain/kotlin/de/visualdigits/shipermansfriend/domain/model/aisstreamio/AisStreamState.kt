package de.visualdigits.shipermansfriend.domain.model.aisstreamio

import androidx.compose.ui.graphics.Color
import co.touchlab.kermit.Severity

/**
 * Determines whether the aisstream.io service is up or down
 * according to the inofficial status API.
 */
enum class AisStreamState(
    val severity: Severity,
    val color: Color
) {

    Up(Severity.Info, Color(0xFF00FF00)),
    Down(Severity.Error, Color(0xFFFF0000))
}
