package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.Destinations
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoordinatedUTCInquiry(
    @SerialName("Destinations") val destinations: Destinations? = null,
    @SerialName("MessageID") val messageId: Int,
    @SerialName("RepeatIndicator") val repeatIndicator: Int,
    @SerialName("Spare") val spare: Int = 0,
    @SerialName("UserID") val mmsi: Int,
    @SerialName("Valid") val valid: Boolean
) : AisMessageData
