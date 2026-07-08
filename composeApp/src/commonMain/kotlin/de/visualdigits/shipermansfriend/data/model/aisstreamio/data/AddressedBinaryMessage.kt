package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.ApplicationID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressedBinaryMessage(
    @SerialName("ApplicationID") val applicationID: ApplicationID,
    @SerialName("BinaryData") val binaryData: String,
    @SerialName("DestinationID") val destinationMmsi: Long,
    @SerialName("MessageID") val messageId: Long,
    @SerialName("RepeatIndicator") val repeatIndicator: Long,
    @SerialName("Retransmission") val retransmission: Boolean,
    @SerialName("Sequenceinteger") val sequenceInteger: Long,
    @SerialName("Spare") val spare: Boolean,
    @SerialName("UserID") val mmsi: Long,
    @SerialName("Valid") val valid: Boolean
) : AisMessageData
