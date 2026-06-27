package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.common.domain.model.geodata.Location
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StandardClassBPositionReport(
    @SerialName("AssignedMode") val assignedMode: Boolean,
    @SerialName("ClassBBand") val classBBand: Boolean,
    @SerialName("ClassBDisplay") val classBDisplay: Boolean,
    @SerialName("ClassBDsc") val classBDsc: Boolean,
    @SerialName("ClassBMsg22") val classBMsg22: Boolean,
    @SerialName("ClassBUnit") val classBUnit: Boolean,
    @SerialName("Cog") override val cog: Double,
    @SerialName("CommunicationState") val communicationState: Int,
    @SerialName("CommunicationStateIsItdma") val communicationStateIsItdma: Boolean,
    @SerialName("Latitude") val latitude: Double,
    @SerialName("Longitude") val longitude: Double,
    @SerialName("MessageID") val messageID: Int,
    @SerialName("PositionAccuracy") val positionAccuracy: Boolean,
    @SerialName("Raim") val raim: Boolean,
    @SerialName("RepeatIndicator") val repeatIndicator: Int,
    @SerialName("Sog") override val sog: Double,
    @SerialName("Spare1") val spare1: Int,
    @SerialName("Spare2") val spare2: Int,
    @SerialName("Timestamp") override val timestamp: Int,
    @SerialName("TrueHeading") override val trueHeading: Int,
    @SerialName("UserID") val userID: Int,
    @SerialName("Valid") val valid: Boolean
) : PositionAisMessageData {

    override val location: Location
        get() = Location(
            latitude = latitude,
            longitude = longitude
        )

    override val isMoored: Boolean
        get() = sog < 0.5
}
