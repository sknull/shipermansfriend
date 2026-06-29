package de.visualdigits.shipermansfriend.domain.model.geodata

import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType

class SafetyData(
    messageType: MessageType,
    mmsi: Long,
    val location: Location,
    val messageId: Int,
    val repeatIndicator: Int,
    val valid: Boolean,
    val text: String? = null,
) : AisData(
    messageType = messageType,
    name = "",
    mmsi = mmsi,
    timeUtc = KmpOffsetDateTime.now()
) {
    override fun toString(): String {
        return "SafetyData(messageType='$messageType', name='$name', mmsi=$mmsi, timeUtc=$timeUtc, location=$location, messageId=$messageId, repeatIndicator=$repeatIndicator, valid=$valid, text=$text)"
    }
}

