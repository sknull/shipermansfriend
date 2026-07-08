package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.NavigationalStatus
import de.visualdigits.shipermansfriend.domain.model.geodata.FixType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseStationReport(
    @SerialName("CommunicationState") val communicationState: Long,
    @SerialName("FixType") val fixType: FixType,
    @SerialName("Latitude") val latitude: Double,
    @SerialName("LongRangeEnable") val longRangeEnable: Boolean,
    @SerialName("Longitude") val longitude: Double,
    @SerialName("MessageID") val messageId: Long,
    @SerialName("PositionAccuracy") val positionAccuracy: Boolean,
    @SerialName("Raim") val raim: Boolean,
    @SerialName("RepeatIndicator") val repeatIndicator: Long,
    @SerialName("Spare") val spare: Long,
    @SerialName("UserID") val mmsi: Long,
    @SerialName("UtcDay") val utcDay: Long,
    @SerialName("UtcHour") val utcHour: Long,
    @SerialName("UtcMinute") val utcMinute: Long,
    @SerialName("UtcMonth") val utcMonth: Long,
    @SerialName("UtcSecond") val utcSecond: Long,
    @SerialName("UtcYear") val utcYear: Long,
    @SerialName("Valid") val valid: Boolean
) : PositionAisMessageData {

    override val sog = 0.0
    override val cog = 0.0
    override val navigationalStatus = NavigationalStatus.UNDEFINED
    override val rateOfTurn = 0L
    override val trueHeading = 0L
    override val timestamp = 0L

    override val location: Location
        get() = Location(
            latitude = latitude,
            longitude = longitude
        )

    override val isMoored: Boolean
        get() = sog < 0.5
}

