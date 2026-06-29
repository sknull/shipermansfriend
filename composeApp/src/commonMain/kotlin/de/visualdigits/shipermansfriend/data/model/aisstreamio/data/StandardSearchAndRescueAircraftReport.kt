package de.visualdigits.shipermansfriend.data.model.aisstreamio.data


import de.visualdigits.common.domain.model.geodata.Location
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StandardSearchAndRescueAircraftReport(
    @SerialName("AltFromBaro") val altFromBaro: Boolean,
    @SerialName("Altitude") val altitude: Int,
    @SerialName("AssignedMode") val assignedMode: Boolean,
    @SerialName("Cog") override val cog: Double,
    @SerialName("CommunicationState") val communicationState: Int,
    @SerialName("CommunicationStateIsItdma") val communicationStateIsItdma: Boolean,
    @SerialName("Dte") val dte: Boolean,
    @SerialName("Latitude") val latitude: Double,
    @SerialName("Longitude") val longitude: Double,
    @SerialName("MessageID") val messageId: Int,
    @SerialName("PositionAccuracy") val positionAccuracy: Boolean,
    @SerialName("Raim") val raim: Boolean,
    @SerialName("RepeatIndicator") val repeatIndicator: Int,
    @SerialName("Sog") override val sog: Double,
    @SerialName("Spare1") val spare1: Int,
    @SerialName("Spare2") val spare2: Int,
    @SerialName("Timestamp") override val timestamp: Int,
    @SerialName("UserID") val mmsi: Int,
    @SerialName("Valid") val valid: Boolean
) : PositionAisMessageData {

    override val trueHeading: Int = 0

    override val location: Location
        get() = Location(
            latitude = latitude,
            longitude = longitude
        )

    override val isMoored: Boolean
        get() = sog < 0.5
}

