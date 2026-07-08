package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.NavigationalStatus
import de.visualdigits.shipermansfriend.domain.model.geodata.SpecialManoeuvreIndicator
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PositionReport(
    @SerialName("MessageID") val messageId: Long,
    @SerialName("UserID") val mmsi: Long,
    @SerialName("Latitude") val latitude: Double,
    @SerialName("Longitude") val longitude: Double,
    @SerialName("Sog") override val sog: Double,
    @SerialName("Cog") override val cog: Double,
    @SerialName("TrueHeading") override val trueHeading: Long,
    @SerialName("NavigationalStatus") override val navigationalStatus: NavigationalStatus,
    @SerialName("RateOfTurn") override val rateOfTurn: Long,
    @SerialName("Timestamp") override val timestamp: Long,
    @SerialName("PositionAccuracy") val positionAccuracy: Boolean,
    @SerialName("Raim") val raim: Boolean,
    @SerialName("CommunicationState") val communicationState: Long,
    @SerialName("RepeatIndicator") val repeatIndicator: Long,
    @SerialName("Spare") val spare: Long,
    @SerialName("SpecialManoeuvreIndicator") val specialManoeuvreIndicator: SpecialManoeuvreIndicator,
    @SerialName("Valid") val calid: Boolean
) : PositionAisMessageData {

    override val location: Location
        get() = Location(
            latitude = latitude,
            longitude = longitude
        )

    override val isMoored: Boolean
        get() = sog < 0.5
}
