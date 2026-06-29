package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.Dimension
import de.visualdigits.shipermansfriend.domain.model.geodata.FixType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExtendedClassBPositionReport(
    @SerialName("AssignedMode") val assignedMode: Boolean,
    @SerialName("Cog") override val cog: Double,
    @SerialName("Dimension") val dimension: Dimension,
    @SerialName("Dte") val dte: Boolean,
    @SerialName("FixType") val fixType: FixType,
    @SerialName("Latitude") val latitude: Double,
    @SerialName("Longitude") val longitude: Double,
    @SerialName("MessageID") val messageId: Int,
    @SerialName("Name") val name: String,
    @SerialName("PositionAccuracy") val positionAccuracy: Boolean,
    @SerialName("Raim") val raim: Boolean,
    @SerialName("RepeatIndicator") val repeatIndicator: Int,
    @SerialName("Sog") override val sog: Double,
    @SerialName("Spare1") val spare1: Int,
    @SerialName("Spare2") val spare2: Int,
    @SerialName("Spare3") val spare3: Int,
    @SerialName("Timestamp") override val timestamp: Int,
    @SerialName("TrueHeading") override val trueHeading: Int,
    @SerialName("Type") val type: Int,
    @SerialName("UserID") val mmsi: Int,
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
