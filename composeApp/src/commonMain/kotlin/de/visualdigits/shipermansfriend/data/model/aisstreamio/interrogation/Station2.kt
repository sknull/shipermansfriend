package de.visualdigits.shipermansfriend.data.model.aisstreamio.interrogation


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Station2(
    @SerialName("MessageID") val messageID: Int,
    @SerialName("SlotOffset") val slotOffset: Int,
    @SerialName("Spare1") val spare1: Int,
    @SerialName("Spare2") val spare2: Int,
    @SerialName("StationID") val stationID: Int,
    @SerialName("Valid") val valid: Boolean
)
