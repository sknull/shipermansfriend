package de.visualdigits.shipermansfriend.domain.model.geodata

import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType

class SafetyData(
    messageType: MessageType,
    val messageID :Int,
    val repeatIndicator :Int,
    val userID :Int,
    val valid :Boolean,
    val text: String,
) : AisData(
    messageType,
    "",
    0,
    timeUtc = KmpOffsetDateTime.now()
) {
    override fun toString(): String {
        return "SafetyData(messageType=$messageType, messageID=$messageID, repeatIndicator=$repeatIndicator, userID=$userID, valid=$valid, text='$text', timeUtc='$timeUtc')"
    }
}

