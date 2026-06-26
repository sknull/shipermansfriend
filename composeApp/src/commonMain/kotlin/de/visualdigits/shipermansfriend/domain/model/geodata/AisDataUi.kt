package de.visualdigits.shipermansfriend.domain.model.geodata

import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import kotlin.math.cos
import kotlin.math.sin

data class AisDataUi(
    val name: String,
    val mmsi: Long,
    val timeUtc: KmpOffsetDateTime,

    val location: Location,
    val isMoored: Boolean = true,
    val sog: Double = 0.0,
    val heading: Double = 0.0,

    val imoNumber: Long? = null,
    val callSign: String? = null,
    val destination: String? = null,
    val totalLength: Long? = null,
    val totalWidth: Long? = null,
    val shipType: ShipType? = null,
    val maximumStaticDraught: Double? = null,

    val distance: Double,
    val distanceString: String
) {

    override fun toString(): String {
        return "${name} mmsi=${mmsi} timeUtc=${timeUtc}, imo=${imoNumber}, type=${shipType?.category?.name}, dest=${destination}, maxDraught=${maximumStaticDraught}"
    }

    val extrapolatedPosition: Location
        get() {
            if (sog <= 0.1) return location // Schiff steht oder liegt vor Anker

            // 1. Zeitdifferenz in Sekunden berechnen
            val currentTime = KmpOffsetDateTime.now()
            val secondsElapsed = currentTime.minus(timeUtc).inWholeSeconds

            // Sicherheitsnetz: Wenn das Signal seit 10 Minuten weg ist, nicht unendlich weiterrechnen
            if (secondsElapsed > 600) return location

            // 2. Geschwindigkeit von Knoten in Meter pro Sekunde umrechnen (1 Knoten ≈ 0.514444 m/s)
            val speedMetersPerSecond = sog * 0.514444
            val distanceTraveledMeters = speedMetersPerSecond * secondsElapsed

            // 3. Erdradius in Metern
            val earthRadius = 6371000.0

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
}
