package de.visualdigits.shipermansfriend.data.model.aisstreamio.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SafetyBroadcastMessage(
    @SerialName("MessageID") override val messageId: Int,
    @SerialName("RepeatIndicator") override val repeatIndicator: Int,
    @SerialName("Spare") val spare: Int = 0,
    @SerialName("Text") override val text: String,
    @SerialName("UserID") override val mmsi: Long,
    @SerialName("Valid") override val valid: Boolean
) : SafetyAisMessageData
