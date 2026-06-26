package de.visualdigits.shipermansfriend.data.model.aisstreamio.standardclassbpositionreport


import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.data.model.aisstreamio.position.PositionAisMessageData
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
) : PositionAisMessageData<StandardClassBPositionReportContainer> {

    override val location: Location
        get() = Location(
            latitude = latitude,
            longitude = longitude
        )

    override val isMoored: Boolean
        get() = sog < 0.5

    override fun toString(): String {
        return if (sog > 0.5) {
            "Ship class b position: [${Location(latitude, longitude).toDmsString()}] cruising with ${sog} knots${if (cog != 3600.0) " course is ${cog / 10.0}°" else ""}."
        } else {
            "Ship class b position: [${Location(latitude, longitude).toDmsString()}] is moored."
        }
    }
}
