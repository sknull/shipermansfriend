package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import androidx.compose.runtime.Immutable
import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.Destinations
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class CoordinatedUTCInquiry(
    @SerialName("Destinations") val destinations: Destinations? = null,
    @SerialName("MessageID") val messageId: Long,
    @SerialName("RepeatIndicator") val repeatIndicator: Long,
    @SerialName("Spare") val spare: Long = 0,
    @SerialName("UserID") val mmsi: Long,
    @SerialName("Valid") val valid: Boolean
) : AisMessageData
