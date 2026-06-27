package de.visualdigits.shipermansfriend.data.model.aisstreamio

import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.Instant

@Serializable
data class AisMetaData(
    @SerialName("MMSI") val mmsi: Long,
    @SerialName("MMSI_String") val mmsiString: Long = 0,
    @SerialName("ShipName") val shipName: String,
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("time_utc") val timeUtc: String
) {

    val location: Location
        get() = Location(
            latitude = latitude,
            longitude = longitude
        )

    val dateTime: KmpOffsetDateTime
        get() {
            return try {
                val isoString = timeUtc
                    .replaceFirst(" ", "T")
                    .substringBefore(" +") + "Z"

                KmpOffsetDateTime(Instant.parse(isoString))
            } catch (_: Exception) {
                KmpOffsetDateTime(Clock.System.now())
            }
        }

    override fun toString(): String {
        return "${shipName.trim()} [https://www.myshiptracking.com/vessels/$mmsi-mmsi-$mmsi-imo-] dateTime=$dateTime location=${
            Location(
                latitude,
                longitude
            ).toDmsString()}"
    }
}
