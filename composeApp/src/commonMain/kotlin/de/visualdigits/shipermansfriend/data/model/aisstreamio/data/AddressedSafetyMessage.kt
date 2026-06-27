package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressedSafetyMessage(
    @SerialName("MessageID") val messageID :Int,
    @SerialName("RepeatIndicator") val repeatIndicator :Int,
    @SerialName("UserID") val userID :Int,
    @SerialName("Valid") val valid :Boolean,
    @SerialName("SequenceInt") val sequenceInt :Int = 0,
    @SerialName("DestinationID") val destinationID :Int,
    @SerialName("Retransmission") val retransmission :Boolean,
    @SerialName("Spare") val spare :Boolean,
    @SerialName("Text") val text: String
) : AisMessageData
