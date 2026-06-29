package de.visualdigits.shipermansfriend.domain.model.geodata

import androidx.compose.ui.geometry.Size
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType
import org.jetbrains.compose.resources.StringResource
import kotlin.math.cos
import kotlin.math.sin

const val KILOMETERS_PER_HOUR = 1.852

private const val RADIUS_EARTH = 6371000.0
private const val METERS_PER_SECOND = 0.514444

private const val MAX_EXTRAPOLATION_TIME = 600
private const val MAX_EXTRAPOLATION_DISTANCE = 150.0
private const val MAX_LINEAR_EXTRAPOLATION_TIME = 20
private const val DAMPING_FACTOR = 0.05

data class AisDataUi(
    val messageType: MessageType,

    val name: String = "",
    val safetyNote: StringResource? = null,

    val mmsi: Long,
    val mmsiCountryPrefix: MmsiCountryPrefix,

    val timeUtc: KmpOffsetDateTime,

    val location: Location,
    val isMoored: Boolean = true,
    val sog: Double = 0.0,
    val speedKmh: String = "",
    val heading: Double = 0.0,

    val imoNumber: Long? = null,
    val callSign: String? = null,
    val destination: String? = null,
    val totalLength: Long? = null,
    val totalWidth: Long? = null,
    val shipType: ShipType? = null,
    val maximumStaticDraught: Double? = null,

    val distance: Double,
    val distanceString: String,

    val hasSafetyMessage: Boolean = false,
    val messageId: Int? = null,
    val repeatIndicator: Int? = null,
    val valid: Boolean? = null,
    val text: String? = null,
) {
    companion object {

        fun isValidImo(imo: Long?): Boolean {
            val imoStr = imo?.toString()
            if (imoStr?.length != 7) return false

            val digits = imoStr.map { it.toString().toInt() }
            val sum = (digits[0] * 7) +
                    (digits[1] * 6) +
                    (digits[2] * 5) +
                    (digits[3] * 4) +
                    (digits[4] * 3) +
                    (digits[5] * 2)

            return (sum % 10) == digits[6]
        }
    }

    override fun toString(): String {
        return "$name safetyNote=${safetyNote} mmsi=${mmsi} timeUtc=${timeUtc}, imo=${imoNumber}, type=${shipType?.category?.name}, dest=${destination}, maxDraught=${maximumStaticDraught}"
    }

    fun extrapolatedPosition(): Location {
        if (sog <= 0.5) return location // Schiff steht oder liegt vor Anker

        // 1. Zeitdifferenz in Sekunden berechnen
        val currentTime = KmpOffsetDateTime.now()
        val secondsElapsed = currentTime.minus(timeUtc).inWholeSeconds

        val adjustedSog = if (secondsElapsed > MAX_LINEAR_EXTRAPOLATION_TIME) {
            // Exponentieller Zerfall: Nach 60 Sekunden ist die SOG fast auf 0 gedämpft
            val lambda = DAMPING_FACTOR
            sog * kotlin.math.exp(-lambda * (secondsElapsed - MAX_LINEAR_EXTRAPOLATION_TIME))
        } else {
            sog
        }

        // Sicherheitsnetz: Wenn das Signal seit 10 Minuten weg ist, nicht unendlich weiterrechnen
        if (secondsElapsed > MAX_EXTRAPOLATION_TIME) return location

        // 2. Geschwindigkeit von Knoten in Meter pro Sekunde umrechnen (1 Knoten ≈ 0.514444 m/s)
        val speedMetersPerSecond = adjustedSog * METERS_PER_SECOND
        val distanceTraveledMeters = (speedMetersPerSecond * secondsElapsed).coerceAtMost(MAX_EXTRAPOLATION_DISTANCE)

        // 3. Erdradius in Metern
        val earthRadius = RADIUS_EARTH

        // 4. Kurs in Bogenmaß (Radiant) umrechnen
        val courseRad = Math.toRadians(heading)

        // 5. Breitengrad (Latitude) hochrechnen
        val deltaLat = (distanceTraveledMeters * cos(courseRad)) / earthRadius
        val newLat = location.latitude + Math.toDegrees(deltaLat)

        // 6. Längengrad (Longitude) hochrechnen (abhängig vom Breitengrad)
        val deltaLon =
            (distanceTraveledMeters * sin(courseRad)) / (earthRadius * cos(Math.toRadians(location.latitude)))
        val newLon = location.longitude + Math.toDegrees(deltaLon)

        return Location(latitude = newLat, longitude = newLon)
    }

    fun calculateRadarSize(
        radarRadiusPx: Float,
        maxRadarDistanceMeters: Double,
    ): Size {
        val totalWidthPx = totalWidth?.let { v -> v.toDouble() / maxRadarDistanceMeters * radarRadiusPx } ?: 0.0
        val totalLengthPx = totalLength?.let { v -> v.toDouble() / maxRadarDistanceMeters * radarRadiusPx } ?: 0.0

        return if (totalWidthPx >= 3.0 || totalLengthPx >= 3.0) {
            Size(
                width = totalWidthPx.toFloat(),
                height = totalLengthPx.toFloat()
            )
        } else {
            Size.Unspecified
        }
    }
}
