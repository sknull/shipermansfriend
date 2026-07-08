package de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Station2(
    @SerialName("MessageID") val messageId: Long,
    @SerialName("SlotOffset") val slotOffset: Long,
    @SerialName("Spare1") val spare1: Long,
    @SerialName("Spare2") val spare2: Long,
    @SerialName("StationID") val stationID: Long,
    @SerialName("Valid") val valid: Boolean
)
