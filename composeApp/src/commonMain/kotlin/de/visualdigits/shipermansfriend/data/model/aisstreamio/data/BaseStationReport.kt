package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.FixType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseStationReport(
    @SerialName("CommunicationState") val communicationState: Int,
    @SerialName("FixType") val fixType: FixType,
    @SerialName("Latitude") val latitude: Double,
    @SerialName("LongRangeEnable") val longRangeEnable: Boolean,
    @SerialName("Longitude") val longitude: Double,
    @SerialName("MessageID") val messageId: Int,
    @SerialName("PositionAccuracy") val positionAccuracy: Boolean,
    @SerialName("Raim") val raim: Boolean,
    @SerialName("RepeatIndicator") val repeatIndicator: Int,
    @SerialName("Spare") val spare: Int,
    @SerialName("UserID") val mmsi: Int,
    @SerialName("UtcDay") val utcDay: Int,
    @SerialName("UtcHour") val utcHour: Int,
    @SerialName("UtcMinute") val utcMinute: Int,
    @SerialName("UtcMonth") val utcMonth: Int,
    @SerialName("UtcSecond") val utcSecond: Int,
    @SerialName("UtcYear") val utcYear: Int,
    @SerialName("Valid") val valid: Boolean
) : PositionAisMessageData {

    override val sog = 0.0
    override val cog = 0.0
    override val trueHeading = 0
    override val timestamp = 0

    override val location: Location
        get() = Location(
            latitude = latitude,
            longitude = longitude
        )

    override val isMoored: Boolean
        get() = sog < 0.5
}

