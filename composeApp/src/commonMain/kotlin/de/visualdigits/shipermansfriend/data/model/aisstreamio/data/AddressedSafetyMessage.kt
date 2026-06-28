package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressedSafetyMessage(
    @SerialName("MessageID") override val messageID :Int,
    @SerialName("RepeatIndicator") override val repeatIndicator :Int,
    @SerialName("UserID") override val userID :Int,
    @SerialName("Valid") override val valid :Boolean,
    @SerialName("SequenceInt") val sequenceInt :Int = 0,
    @SerialName("DestinationID") val destinationID :Int,
    @SerialName("Retransmission") val retransmission :Boolean,
    @SerialName("Spare") val spare :Boolean,
    @SerialName("Text") override val text: String
) : SafetyAisMessageData
