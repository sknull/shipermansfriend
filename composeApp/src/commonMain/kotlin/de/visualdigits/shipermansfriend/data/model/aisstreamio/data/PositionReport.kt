package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.common.domain.model.geodata.Location
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.round

@Serializable
data class PositionReport(
    @SerialName("MessageID") val messageID: Int,
    @SerialName("UserID") val userID: Long,
    @SerialName("Latitude") val latitude: Double,
    @SerialName("Longitude") val longitude: Double,
    @SerialName("Sog") override val sog: Double,
    @SerialName("Cog") override val cog: Double,
    @SerialName("TrueHeading") override val trueHeading: Int,
    @SerialName("NavigationalStatus") val navigationalStatus: Int,
    @SerialName("RateOfTurn") val rateOfTurn: Int,
    @SerialName("Timestamp") override val timestamp: Int,
    @SerialName("PositionAccuracy") val positionAccuracy: Boolean,
    @SerialName("Raim") val raim: Boolean,
    @SerialName("CommunicationState") val communicationState: Long,
    @SerialName("RepeatIndicator") val repeatIndicator: Int,
    @SerialName("Spare") val spare: Int,
    @SerialName("SpecialManoeuvreIndicator") val specialManoeuvreIndicator: Int,
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
