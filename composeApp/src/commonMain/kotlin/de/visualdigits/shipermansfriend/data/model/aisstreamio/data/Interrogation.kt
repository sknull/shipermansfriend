package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.Station1Msg
import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.Station2
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Interrogation(
    @SerialName("MessageID") val messageID: Int,
    @SerialName("RepeatIndicator") val repeatIndicator: Int,
    @SerialName("Spare") val spare: Int,
    @SerialName("Station1Msg1") val station1Msg1: Station1Msg,
    @SerialName("Station1Msg2") val station1Msg2: Station1Msg,
    @SerialName("Station2") val station2: Station2,
    @SerialName("UserID") val userID: Int,
    @SerialName("Valid") val valid: Boolean
) : AisMessageData
