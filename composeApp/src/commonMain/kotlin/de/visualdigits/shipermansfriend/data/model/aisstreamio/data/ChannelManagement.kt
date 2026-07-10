package de.visualdigits.shipermansfriend.data.model.aisstreamio.data


import androidx.compose.runtime.Immutable
import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.Area
import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.Unicast
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class ChannelManagement(
    @SerialName("Area") val area: Area,
    @SerialName("BwA") val bwA: Boolean,
    @SerialName("BwB") val bwB: Boolean,
    @SerialName("ChannelA") val channelA: Long,
    @SerialName("ChannelB") val channelB: Long,
    @SerialName("IsAddressed") val isAddressed: Boolean,
    @SerialName("LowPower") val lowPower: Boolean,
    @SerialName("MessageID") val messageId: Long,
    @SerialName("RepeatIndicator") val repeatIndicator: Long,
    @SerialName("Spare1") val spare1: Long,
    @SerialName("Spare4") val spare4: Long,
    @SerialName("TransitionalZoneSize") val transitionalZoneSize: Long,
    @SerialName("TxRxMode") val txRxMode: Long,
    @SerialName("Unicast") val unicast: Unicast,
    @SerialName("UserID") val mmsi: Long,
    @SerialName("Valid") val valid: Boolean
) : AisMessageData
