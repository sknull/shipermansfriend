package de.visualdigits.shipermansfriend.domain.model.geodata

import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType

open class AisData(
    val messageType: MessageType,
    val name: String,
    val mmsi: Long,
    val timeUtc: KmpOffsetDateTime
) {
    override fun toString(): String {
        return "AisData(messageType='$messageType', name='$name', mmsi=$mmsi, timeUtc=$timeUtc)"
    }
}
