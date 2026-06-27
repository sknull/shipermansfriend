package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LongRangeAisBroadcastMessage(
    @SerialName("Latitude1") val latitude1: Double,
    @SerialName("Latitude2") val latitude2: Double,
    @SerialName("Longitude1") val longitude1: Double,
    @SerialName("Longitude2") val longitude2: Double,
    @SerialName("MessageID") val messageID: Int,
    @SerialName("QuietTime") val quietTime: Int,
    @SerialName("RepeatIndicator") val repeatIndicator: Int,
    @SerialName("ReportingInterval") val reportingInterval: Int,
    @SerialName("ShipType") val shipType: Int,
    @SerialName("Spare1") val spare1: Int,
    @SerialName("Spare2") val spare2: Int,
    @SerialName("Spare3") val spare3: Int,
    @SerialName("StationType") val stationType: Int,
    @SerialName("TxRxMode") val txRxMode: Int,
    @SerialName("UserID") val userID: Int,
    @SerialName("Valid") val valid: Boolean
) : AisMessageData
