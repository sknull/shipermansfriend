package de.visualdigits.shipermansfriend.data.model.aisstreamio.data


import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.NavigationalStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StandardSearchAndRescueAircraftReport(
    @SerialName("AltFromBaro") val altFromBaro: Boolean,
    @SerialName("Altitude") val altitude: Long,
    @SerialName("AssignedMode") val assignedMode: Boolean,
    @SerialName("Cog") override val cog: Double,
    @SerialName("CommunicationState") val communicationState: Long,
    @SerialName("CommunicationStateIsItdma") val communicationStateIsItdma: Boolean,
    @SerialName("Dte") val dte: Boolean,
    @SerialName("Latitude") val latitude: Double,
    @SerialName("Longitude") val longitude: Double,
    @SerialName("MessageID") val messageId: Long,
    @SerialName("PositionAccuracy") val positionAccuracy: Boolean,
    @SerialName("Raim") val raim: Boolean,
    @SerialName("RepeatIndicator") val repeatIndicator: Long,
    @SerialName("Sog") override val sog: Double,
    @SerialName("Spare1") val spare1: Long,
    @SerialName("Spare2") val spare2: Long,
    @SerialName("Timestamp") override val timestamp: Long,
    @SerialName("UserID") val mmsi: Long,
    @SerialName("Valid") val valid: Boolean
) : PositionAisMessageData {

    override val navigationalStatus = NavigationalStatus.UNDEFINED
    override val rateOfTurn = 0L
    override val trueHeading = 0L

    override val location: Location
        get() = Location(
            latitude = latitude,
            longitude = longitude
        )

    override val isMoored: Boolean
        get() = sog < 0.5
}

