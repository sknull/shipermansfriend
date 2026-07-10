package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import androidx.compose.runtime.Immutable
import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.ApplicationID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class MultiSlotBinaryMessage(
    @SerialName("ApplicationID") val applicationID: ApplicationID? = null,
    @SerialName("ApplicationIDValid") val applicationIDValid: Boolean? = null,
    @SerialName("CommunicationState") val communicationState: Long? = null,
    @SerialName("CommunicationStateIsItdma") val communicationStateIsItdma: Boolean? = null,
    @SerialName("DestinationID") val destinationMmsi: Long? = null,
    @SerialName("DestinationIDValid") val destinationMmsiValid: Boolean? = null,
    @SerialName("MessageID") val messageId: Long? = null,
    @SerialName("Payload") val payload: String? = null,
    @SerialName("RepeatIndicator") val repeatIndicator: Long? = null,
    @SerialName("Spare1") val spare1: Long? = null,
    @SerialName("Spare2") val spare2: Long? = null,
    @SerialName("UserID") val mmsi: Long? = null,
    @SerialName("Valid") val valid: Boolean? = null
) : AisMessageData
