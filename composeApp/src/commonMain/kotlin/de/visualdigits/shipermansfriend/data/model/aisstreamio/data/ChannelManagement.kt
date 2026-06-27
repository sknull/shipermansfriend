package de.visualdigits.shipermansfriend.data.model.aisstreamio.data


import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.Area
import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.Unicast
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChannelManagement(
    @SerialName("Area") val area: Area,
    @SerialName("BwA") val bwA: Boolean,
    @SerialName("BwB") val bwB: Boolean,
    @SerialName("ChannelA") val channelA: Int,
    @SerialName("ChannelB") val channelB: Int,
    @SerialName("IsAddressed") val isAddressed: Boolean,
    @SerialName("LowPower") val lowPower: Boolean,
    @SerialName("MessageID") val messageID: Int,
    @SerialName("RepeatIndicator") val repeatIndicator: Int,
    @SerialName("Spare1") val spare1: Int,
    @SerialName("Spare4") val spare4: Int,
    @SerialName("TransitionalZoneSize") val transitionalZoneSize: Int,
    @SerialName("TxRxMode") val txRxMode: Int,
    @SerialName("Unicast") val unicast: Unicast,
    @SerialName("UserID") val userID: Int,
    @SerialName("Valid") val valid: Boolean
) : AisMessageData
