package de.visualdigits.shipermansfriend.domain.model.aisstreamio

import androidx.compose.ui.graphics.Color
import de.visualdigits.shipermansfriend.presentation.style.IndicatorColor
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

/**
 * Determines whether the socket receives data from the aisstream.io service or not
 * regardless of the service status.
 */
enum class ReceivingDataState(
    val color: Color
) {

    receivingData(Color(0xFF00FF00)),
    noData(Color(0xFFFFFF00)),
    disconnected(Color(0xFFFF0000))
    ;
}
