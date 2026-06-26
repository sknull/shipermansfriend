package de.visualdigits.shipermansfriend.data.model.aisstreamio.interrogation


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Station1Msg(
    @SerialName("MessageID") val messageID: Int,
    @SerialName("SlotOffset") val slotOffset: Int,
    @SerialName("StationID") val stationID: Int? = null,
    @SerialName("Valid") val valid: Boolean
)
