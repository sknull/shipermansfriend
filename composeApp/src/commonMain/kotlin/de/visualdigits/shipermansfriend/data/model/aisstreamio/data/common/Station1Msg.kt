package de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Station1Msg(
    @SerialName("MessageID") val messageId: Long,
    @SerialName("SlotOffset") val slotOffset: Long,
    @SerialName("StationID") val stationID: Long? = null,
    @SerialName("Valid") val valid: Boolean
)
