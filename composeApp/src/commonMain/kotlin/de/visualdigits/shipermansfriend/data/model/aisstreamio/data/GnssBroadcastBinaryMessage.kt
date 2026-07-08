package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.NavigationalStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GnssBroadcastBinaryMessage(
    @SerialName("MessageID") val messageId: Long,
    @SerialName("RepeatIndicator") val repeatIndicator: Long,
    @SerialName("UserID") val mmsi: Long,
    @SerialName("Valid") val valid: Boolean,
    @SerialName("Spare1") val spare1: Long,
    @SerialName("Spare2") val spare2: Long,
    @SerialName("Longitude") val longitude: Double,
    @SerialName("Latitude") val latitude: Double,
    @SerialName("Data") val data: String,
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
