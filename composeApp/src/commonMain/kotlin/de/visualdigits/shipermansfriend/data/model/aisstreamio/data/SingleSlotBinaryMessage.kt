package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import androidx.compose.runtime.Immutable
import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.ApplicationID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class SingleSlotBinaryMessage(
    @SerialName("ApplicationID") val applicationID: ApplicationID,
    @SerialName("ApplicationIDValid") val applicationIDValid: Boolean,
    @SerialName("DestinationID") val destinationMmsi: Long,
    @SerialName("DestinationIDValid") val destinationMmsiValid: Boolean,
    @SerialName("MessageID") val messageId: Long,
    @SerialName("Payload") val payload: String,
    @SerialName("RepeatIndicator") val repeatIndicator: Long,
    @SerialName("Spare") val spare: Long,
    @SerialName("UserID") val mmsi: Long,
    @SerialName("Valid") val valid: Boolean
) : AisMessageData
