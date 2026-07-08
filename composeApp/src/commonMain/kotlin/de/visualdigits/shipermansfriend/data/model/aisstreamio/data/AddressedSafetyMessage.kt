package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressedSafetyMessage(
    @SerialName("MessageID") override val messageId :Long,
    @SerialName("RepeatIndicator") override val repeatIndicator :Long,
    @SerialName("UserID") override val mmsi: Long,
    @SerialName("Valid") override val valid :Boolean,
    @SerialName("SequenceInt") val sequenceInt :Long = 0,
    @SerialName("DestinationID") val destinationMmsi :Long,
    @SerialName("Retransmission") val retransmission :Boolean,
    @SerialName("Spare") val spare :Boolean,
    @SerialName("Text") override val text: String
) : SafetyAisMessageData
