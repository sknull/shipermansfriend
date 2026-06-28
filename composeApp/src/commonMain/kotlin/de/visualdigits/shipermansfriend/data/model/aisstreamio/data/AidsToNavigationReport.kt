package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.Dimension
import de.visualdigits.shipermansfriend.domain.model.geodata.FixType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AidsToNavigationReport(
    @SerialName("AssignedMode") val assignedMode: Boolean,
    @SerialName("AtoN") val atoN: Int,
    @SerialName("Dimension") val dimension: Dimension,
    @SerialName("Fixtype") val fixtype: FixType,
    @SerialName("Latitude") val latitude: Double,
    @SerialName("Longitude") val longitude: Double,
    @SerialName("MessageID") val messageID: Int,
    @SerialName("Name") val name: String,
    @SerialName("NameExtension") val nameExtension: String,
    @SerialName("OffPosition") val offPosition: Boolean,
    @SerialName("PositionAccuracy") val positionAccuracy: Boolean,
    @SerialName("Raim") val raim: Boolean,
    @SerialName("RepeatIndicator") val repeatIndicator: Int,
    @SerialName("Spare") val spare: Boolean,
    @SerialName("Timestamp") override val timestamp: Int,
    @SerialName("Type") val type: Int,
    @SerialName("UserID") val userID: Int,
    @SerialName("Valid") val valid: Boolean,
    @SerialName("VirtualAtoN") val virtualAtoN: Boolean
) : PositionAisMessageData {

    override val sog = 0.0
    override val cog = 0.0
    override val trueHeading = 0

    override val location: Location
        get() = Location(
            latitude = latitude,
            longitude = longitude
        )

    override val isMoored: Boolean = true
}
