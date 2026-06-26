package de.visualdigits.shipermansfriend.data.model.aisstreamio.basestationreport


import de.visualdigits.shipermansfriend.data.model.aisstreamio.common.AisMessageData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseStationReport(
    @SerialName("CommunicationState") val communicationState: Int,
    @SerialName("FixType") val fixType: Int,
    @SerialName("Latitude") val latitude: Double,
    @SerialName("LongRangeEnable") val longRangeEnable: Boolean,
    @SerialName("Longitude") val longitude: Double,
    @SerialName("MessageID") val messageID: Int,
    @SerialName("PositionAccuracy") val positionAccuracy: Boolean,
    @SerialName("Raim") val raim: Boolean,
    @SerialName("RepeatIndicator") val repeatIndicator: Int,
    @SerialName("Spare") val spare: Int,
    @SerialName("UserID") val userID: Int,
    @SerialName("UtcDay") val utcDay: Int,
    @SerialName("UtcHour") val utcHour: Int,
    @SerialName("UtcMinute") val utcMinute: Int,
    @SerialName("UtcMonth") val utcMonth: Int,
    @SerialName("UtcSecond") val utcSecond: Int,
    @SerialName("UtcYear") val utcYear: Int,
    @SerialName("Valid") val valid: Boolean
) : AisMessageData<BaseStationReportContainer> {
    override fun toString(): String {
        return "Base station report: [lat=${latitude},lon=${longitude}]."
    }
}
