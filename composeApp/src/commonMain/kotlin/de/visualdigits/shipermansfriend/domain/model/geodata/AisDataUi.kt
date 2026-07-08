package de.visualdigits.shipermansfriend.domain.model.geodata

import androidx.compose.ui.geometry.Size
import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType
import de.visualdigits.shipermansfriend.domain.model.geodata.mmsi.MmsiCountryPrefix
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.Country
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortRegistry
import org.jetbrains.compose.resources.StringResource
import kotlin.math.cos
import kotlin.math.sin


data class AisDataUi(
    val messageType: MessageType,

    val name: String = "",
    val safetyNote: StringResource? = null,

    val mmsi: Long,
    val mmsiCountryPrefix: MmsiCountryPrefix,

    val timeUtc: KmpOffsetDateTime,
    var timeUtcObserved: KmpOffsetDateTime? = null,

    val location: Location,
    val sog: Double = 0.0,
    val speedKmh: String = "",
    val heading: Double = 0.0,

    val imoNumber: Long? = null,
    val callSign: String? = null,
    val destination: String? = null,
    val totalLength: Long? = null,
    val totalWidth: Long? = null,
    val shipType: ShipType,
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

        private const val RADIUS_EARTH_METERS = 6371000.0
        private const val METERS_PER_SECOND = 0.514444
        private const val METERS_PER_FRAME = METERS_PER_SECOND / 1000.0 * 40.0 // 25 fps

        private const val MAX_EXTRAPOLATION_TIME_SECONDS = 300
        private const val MAX_EXTRAPOLATION_DISTANCE_METERS = 500.0
        private const val MAX_EXTRAPOLATION_FRAMES = MAX_EXTRAPOLATION_TIME_SECONDS * 1000 / 40

        private val P_POB1 = "POB (\\d+)".toRegex()
        private val P_POB2 = "(\\d+)POB".toRegex()
        private val P_STRING = "([a-zA-Z]+)".toRegex()
        private val P_TIME = "(\\d+:\\d+)".toRegex()

        val CRITICAL_SAFETY_MESSAGES = mapOf(
            "ALERT" to "",
            "DISTRESS" to "Distress Call",
            "EPIRB ACTIVE" to "Electronic Position Indicating Radio Beacon Active",
            "FIRE" to "Fire On Board",
            "MAYDAY" to "",
            "MOB ACTIVE" to "Man Over Board Device Active",
            "RESCUE ALERT" to "Resque Alert",
            "SART ACTIVE" to "Search And Resque Transmitter Active",
        )

        val WARNING_SAFETY_MESSAGES = listOf(
            "CAUTION",
            "CRASH",
            "DO NOT",
            "LIMITED ZONE",
            "MANDATORY",
            "MUST",
            "PLEASE REPORT",
            "RESTRICTED AREA",
            "TARGET SPEED",
            "WARNING",
        )

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

    val isMoored: Boolean
        get() = sog < 0.5

    val uppercase = text?.uppercase()
    val messageSeverity: Severity
        get() = if (CRITICAL_SAFETY_MESSAGES.keys.any { key -> uppercase?.contains(key) == true }) {
            Severity.Error
        } else if (WARNING_SAFETY_MESSAGES.any { key -> uppercase?.contains(key) == true } ) {
            Severity.Warn
        } else {
            Severity.Info
        }

    override fun toString(): String {
        return "AisDataUi(messageType=${messageType.name}, name='$name', safetyNote=$safetyNote, mmsi=$mmsi, mmsiCountryPrefix=$mmsiCountryPrefix, timeUtc=$timeUtc, location=$location, isMoored=$isMoored, sog=$sog, speedKmh='$speedKmh', heading=$heading, imoNumber=$imoNumber, callSign=$callSign, destination=$destination, totalLength=$totalLength, totalWidth=$totalWidth, shipType=${shipType?.category?.name}, maximumStaticDraught=$maximumStaticDraught, distance=$distance, distanceString='$distanceString', hasSafetyMessage=$hasSafetyMessage, messageId=$messageId, repeatIndicator=$repeatIndicator, valid=$valid, text=$text, messageSeverity=$messageSeverity)"
    }

    fun decodedText(): String {
        if (text == null) return ""

        val pob = (P_POB1.find(text)?.groups[1]?.value
            ?: P_POB2.find(text)?.groups[1]?.value)
            ?.let { p ->"Persons on board: $p" }
            ?:""
        val ports = P_STRING.findAll(text)
            .map { m -> m.groups[1]?.value }
            .filter { s -> s?.length == 5 }
            .mapNotNull { s ->
                PortRegistry.findPort(s)
            }
            .joinToString(" - ") { p ->
                "${p.name} (${Country.fromPrefix(p.country)?.countryName ?: p.country})"
            }
        val times = P_TIME.findAll(text)
            .mapNotNull { m -> m.groups[1]?.value }
            .toList()
            .joinToString(" - ")

        return if (pob.isNotBlank() || ports.isNotBlank()) {
            "$ports [$times] $pob"
        } else {
            CRITICAL_SAFETY_MESSAGES[text] ?: text
        }
    }

    /**
     * Extrapolates the location of this vessel at the given time
     * assuming that the latest location was observed in the past.
     *
     * @currentTime The time to use for the calculation (defaults to now)
     *              If you have a lot of vessels to calculate it makes sense
     *              to use a "now" time which was determined before calling
     *              this method for all vessels to avoid jitter.
     */
    fun extrapolatedPosition(
        currentTime: KmpOffsetDateTime = KmpOffsetDateTime.now()
    ): Location {
        if (sog <= 0.5) return location // Schiff steht oder liegt vor Anker

        // 1. Zeitdifferenz in Sekunden berechnen
        val framesElapsed = currentTime.minus(timeUtc).inWholeMilliseconds / 40.0

        // Sicherheitsnetz: Wenn das Signal seit 10 Minuten weg ist, nicht unendlich weiterrechnen
        if (framesElapsed > MAX_EXTRAPOLATION_FRAMES) return location

        // 2. Geschwindigkeit von Knoten in Meter pro Sekunde umrechnen (1 Knoten ≈ 0.514444 m/s)
        val speedMetersPerMillsecond = sog * METERS_PER_FRAME
        val distanceTraveledMeters = (speedMetersPerMillsecond * framesElapsed).coerceAtMost(MAX_EXTRAPOLATION_DISTANCE_METERS)

        // 4. convert bearing to radian
        val courseRad = Math.toRadians(heading)

        // 5. calculate latitude
        val deltaLat = (distanceTraveledMeters * cos(courseRad)) / RADIUS_EARTH_METERS
        val newLat = location.latitude + Math.toDegrees(deltaLat)

        // 6. calculate longitude (depends on latitude)
        val deltaLon =
            (distanceTraveledMeters * sin(courseRad)) / (RADIUS_EARTH_METERS * cos(Math.toRadians(location.latitude)))
        val newLon = location.longitude + Math.toDegrees(deltaLon)

        return Location(latitude = newLat, longitude = newLon)
    }

    /**
     * Calculates the size box for this vessel according to the reported
     * values (if any master data was reported so far for this vessel).
     */
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
