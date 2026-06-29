package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.ApplicationID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SingleSlotBinaryMessage(
    @SerialName("ApplicationID") val applicationID: ApplicationID,
    @SerialName("ApplicationIDValid") val applicationIDValid: Boolean,
    @SerialName("DestinationID") val destinationMmsi: Int,
    @SerialName("DestinationIDValid") val destinationMmsiValid: Boolean,
    @SerialName("MessageID") val messageId: Int,
    @SerialName("Payload") val payload: String,
    @SerialName("RepeatIndicator") val repeatIndicator: Int,
    @SerialName("Spare") val spare: Int,
    @SerialName("UserID") val mmsi: Int,
    @SerialName("Valid") val valid: Boolean
) : AisMessageData
