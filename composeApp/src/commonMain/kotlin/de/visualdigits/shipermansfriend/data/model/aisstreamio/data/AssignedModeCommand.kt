package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.Commands
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssignedModeCommand(
    @SerialName("Commands") val commands: Commands,
    @SerialName("MessageID") val messageId: Int,
    @SerialName("RepeatIndicator") val repeatIndicator: Int,
    @SerialName("Spare") val spare: Int,
    @SerialName("UserID") val mmsi: Int,
    @SerialName("Valid") val valid: Boolean
) : AisMessageData
