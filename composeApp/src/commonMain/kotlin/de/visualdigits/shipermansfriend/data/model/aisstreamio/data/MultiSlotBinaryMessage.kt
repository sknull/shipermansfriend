package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.ApplicationID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MultiSlotBinaryMessage(
    @SerialName("ApplicationID") val applicationID: ApplicationID? = null,
    @SerialName("ApplicationIDValid") val applicationIDValid: Boolean? = null,
    @SerialName("CommunicationState") val communicationState: Int? = null,
    @SerialName("CommunicationStateIsItdma") val communicationStateIsItdma: Boolean? = null,
    @SerialName("DestinationID") val destinationID: Int? = null,
    @SerialName("DestinationIDValid") val destinationIDValid: Boolean? = null,
    @SerialName("MessageID") val messageID: Int? = null,
    @SerialName("Payload") val payload: String? = null,
    @SerialName("RepeatIndicator") val repeatIndicator: Int? = null,
    @SerialName("Spare1") val spare1: Int? = null,
    @SerialName("Spare2") val spare2: Int? = null,
    @SerialName("UserID") val userID: Int? = null,
    @SerialName("Valid") val valid: Boolean? = null
) : AisMessageData
