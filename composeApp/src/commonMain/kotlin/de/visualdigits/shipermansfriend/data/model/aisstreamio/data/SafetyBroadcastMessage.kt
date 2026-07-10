package de.visualdigits.shipermansfriend.data.model.aisstreamio.data


import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class SafetyBroadcastMessage(
    @SerialName("MessageID") override val messageId: Long,
    @SerialName("RepeatIndicator") override val repeatIndicator: Long,
    @SerialName("Spare") val spare: Long = 0,
    @SerialName("Text") override val text: String,
    @SerialName("UserID") override val mmsi: Long,
    @SerialName("Valid") override val valid: Boolean
) : SafetyAisMessageData
