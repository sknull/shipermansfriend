package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import androidx.compose.runtime.Immutable
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.NavigationalStatus
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class LongRangeAisBroadcastMessage(
    @SerialName("Latitude1") val latitude1: Double,
    @SerialName("Latitude2") val latitude2: Double,
    @SerialName("Longitude1") val longitude1: Double,
    @SerialName("Longitude2") val longitude2: Double,
    @SerialName("MessageID") val messageId: Long,
    @SerialName("QuietTime") val quietTime: Long,
    @SerialName("RepeatIndicator") val repeatIndicator: Long,
    @SerialName("ReportingInterval") val reportingInterval: Long,
    @SerialName("ShipType") val shipType: ShipType,
    @SerialName("Spare1") val spare1: Long,
    @SerialName("Spare2") val spare2: Long,
    @SerialName("Spare3") val spare3: Long,
    @SerialName("StationType") val stationType: Long,
    @SerialName("TxRxMode") val txRxMode: Long,
    @SerialName("UserID") val mmsi: Long,
    @SerialName("Valid") val valid: Boolean
) : PositionAisMessageData {

    override val sog = 0.0
    override val cog = 0.0
    override val navigationalStatus = NavigationalStatus.UNDEFINED
    override val rateOfTurn = 0L
    override val trueHeading = 0L
    override val timestamp = 0L

    override val location: Location
        get() = Location(
            latitude = latitude1,
            longitude = longitude1
        )

    val location2: Location
        get() = Location(
            latitude = latitude2,
            longitude = longitude2
        )

    override val isMoored: Boolean
        get() = sog < 0.5
}
