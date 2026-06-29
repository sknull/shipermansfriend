package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.common.domain.model.geodata.Location
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GnssBroadcastBinaryMessage(
    @SerialName("MessageID") val messageId: Int,
    @SerialName("RepeatIndicator") val repeatIndicator: Int,
    @SerialName("UserID") val mmsi: Int,
    @SerialName("Valid") val valid: Boolean,
    @SerialName("Spare1") val spare1: Int,
    @SerialName("Spare2") val spare2: Int,
    @SerialName("Longitude") val longitude: Double,
    @SerialName("Latitude") val latitude: Double,
    @SerialName("Data") val data: String,
) : PositionAisMessageData {

    override val sog: Double = 0.0
    override val cog: Double = 0.0
    override val trueHeading: Int = 0
    override val timestamp: Int = 0

    override val location: Location
        get() = Location(
            latitude = latitude,
            longitude = longitude
        )

    override val isMoored: Boolean
        get() = sog < 0.5
}
