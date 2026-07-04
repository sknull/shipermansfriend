package de.visualdigits.shipermansfriend.domain.model.aisstreamio

import androidx.compose.ui.graphics.Color

/**
 * Determines whether the socket receives data from the aisstream.io service or not
 * regardless of the service status.
 */
enum class ReceivingDataState(
    val color: Color
) {

    receivingData(Color(0xFF00FF00)),
    noData(Color(0xFF333333)),
    disconnected(Color(0xFFFF0000))
    ;
}
