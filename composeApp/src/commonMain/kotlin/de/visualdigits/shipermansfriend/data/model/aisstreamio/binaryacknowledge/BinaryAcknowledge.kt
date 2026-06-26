package de.visualdigits.shipermansfriend.data.model.aisstreamio.binaryacknowledge


import de.visualdigits.shipermansfriend.data.model.aisstreamio.common.AisMessageData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BinaryAcknowledge(
    @SerialName("Destinations") val destinations: Destinations? = Destinations(),
    @SerialName("MessageID") val messageID: Int? = 0,
    @SerialName("RepeatIndicator") val repeatIndicator: Int? = 0,
    @SerialName("Spare") val spare: Int? = 0,
    @SerialName("UserID") val userID: Int? = 0,
    @SerialName("Valid") val valid: Boolean? = false
) : AisMessageData<BinaryAcknowledgeContainer> {
    override fun toString(): String {
        return "Base station report:"
    }
}
