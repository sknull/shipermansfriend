package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.ApplicationID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressedBinaryMessage(
    @SerialName("ApplicationID") val applicationID: ApplicationID,
    @SerialName("BinaryData") val binaryData: String,
    @SerialName("DestinationID") val destinationID: Int,
    @SerialName("MessageID") val messageID: Int,
    @SerialName("RepeatIndicator") val repeatIndicator: Int,
    @SerialName("Retransmission") val retransmission: Boolean,
    @SerialName("Sequenceinteger") val sequenceinteger: Int,
    @SerialName("Spare") val spare: Boolean,
    @SerialName("UserID") val userID: Int,
    @SerialName("Valid") val valid: Boolean
) : AisMessageData
