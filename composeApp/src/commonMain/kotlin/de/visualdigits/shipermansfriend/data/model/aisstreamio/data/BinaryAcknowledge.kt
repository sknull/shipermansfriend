package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.Destinations
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BinaryAcknowledge(
    @SerialName("Destinations") val destinations: Destinations? = Destinations(),
    @SerialName("MessageID") val messageId: Long? = 0,
    @SerialName("RepeatIndicator") val repeatIndicator: Long? = 0,
    @SerialName("Spare") val spare: Long? = 0,
    @SerialName("UserID") val mmsi: Long? = 0,
    @SerialName("Valid") val valid: Boolean? = false
) : AisMessageData
