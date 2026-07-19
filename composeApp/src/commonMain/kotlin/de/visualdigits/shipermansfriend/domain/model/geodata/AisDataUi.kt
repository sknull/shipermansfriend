package de.visualdigits.shipermansfriend.domain.model.geodata

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.util.color
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.label_callsign
import de.visualdigits.compose.resources.label_destination
import de.visualdigits.compose.resources.label_distance
import de.visualdigits.compose.resources.label_imo
import de.visualdigits.compose.resources.label_last_message
import de.visualdigits.compose.resources.label_length
import de.visualdigits.compose.resources.label_location
import de.visualdigits.compose.resources.label_maxDraught
import de.visualdigits.compose.resources.label_message
import de.visualdigits.compose.resources.label_mmsi
import de.visualdigits.compose.resources.label_speed
import de.visualdigits.compose.resources.label_turnRate
import de.visualdigits.compose.resources.label_unit_degree_minute
import de.visualdigits.compose.resources.label_unit_kmh
import de.visualdigits.compose.resources.label_unit_knots
import de.visualdigits.compose.resources.label_unit_meters
import de.visualdigits.compose.resources.label_width
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType
import de.visualdigits.shipermansfriend.domain.model.geodata.mmsi.MmsiCountryPrefix
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortRegistry
import de.visualdigits.shipermansfriend.domain.util.capitalizeWords
import de.visualdigits.shipermansfriend.domain.util.formatDistance
import de.visualdigits.shipermansfriend.domain.util.formatTime
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin


@Immutable
data class AisDataUi(
    val messageType: MessageType,

    val name: String = "",

    val mmsi: Long,
    val mmsiCountryPrefix: MmsiCountryPrefix,

    val timeUtc: KmpOffsetDateTime,
    val timeUtcObserved: KmpOffsetDateTime? = null,

    val location: Location,
    val observingLocation: Location? = null,
    val sog: Double = 0.0,
    val speedKmh: Double = 0.0,
    val heading: Double = 0.0,
    val movementDirection: MovementDirection = MovementDirection.UNKNOWN,
    val rateOfTurnDegreesPerMinute: Double = 0.0,
    val navigationalStatus: NavigationalStatus = NavigationalStatus.UNDEFINED,

    val imoNumber: Long? = null,
    val callSign: String? = null,
    val destination: String? = null,
    val totalLength: Long? = null,
    val totalWidth: Long? = null,
    val shipType: ShipType,
    val maximumStaticDraught: Double? = null,

    val distance: Double,

    val hasSafetyMessage: Boolean = false,
    val messageId: Long? = null,
    val repeatIndicator: Long? = null,
    val valid: Boolean? = null,
    val text: String? = null,
) {
    companion object {

        const val RADIUS_EARTH_METERS = 6371000.0
        const val METERS_PER_SECOND = 0.514444
        const val METERS_PER_FRAME = METERS_PER_SECOND / 1000.0 * 40.0 // 25 fps

        const val MAX_EXTRAPOLATION_TIME_SECONDS = 300
        const val MAX_EXTRAPOLATION_DISTANCE_METERS = 500.0
        const val MAX_EXTRAPOLATION_FRAMES = MAX_EXTRAPOLATION_TIME_SECONDS * 1000 / 40

        private val P_POB1 = "POB (\\d+)".toRegex()
        private val P_POB2 = "(\\d+)POB".toRegex()
        private val P_POB3 = "(\\d+)pob".toRegex()
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

        fun csvTitleRow(): String {
            return "timeUtcObserved;observingLocation;shipCategory;name;mmsi;deviceType;country;callSign;imoNumber;messageType;speedOverGroundKnots;speedOverGroundKmh;heading;destination;totalLength;totalWidth;maximumStaticDraught;vesselLocation;distance"
        }

        fun movementDirection(
            location: Location?,
            vesselLocation: Location,
            isMoored: Boolean,
            heading: Double
        ): MovementDirection {
            if (location == null) return MovementDirection.UNKNOWN
            if (isMoored) return MovementDirection.MOORED

            if (heading >= 360.0 || heading < 0.0) return MovementDirection.UNKNOWN

            // 1. calculate bearing FROM THIS VESSEL TO ME
            val bearingToMe = vesselLocation.bearingTo(location)

            // 2. calculate angle difference between this vessels bearing towards the reference location and the vessels heading
            var angleDiff = (heading - bearingToMe) % 360
            if (angleDiff < 0) angleDiff += 360

            // 3. evalutation: When difference is < 90° or > 270° the bow is headed toward the reference point
            return if (angleDiff in 90.0..270.0) {
                MovementDirection.OUTBOUND
            } else {
                MovementDirection.INBOUND
            }
        }
    }

    val isMoored: Boolean
        get() = sog < 0.5 || navigationalStatus == NavigationalStatus.MOORED

    val uppercase = text?.uppercase()
    val messageSeverity: Severity
        get() = if (CRITICAL_SAFETY_MESSAGES.keys.any { key -> uppercase?.contains(key) == true }) {
            Severity.Error
        } else if (WARNING_SAFETY_MESSAGES.any { key -> uppercase?.contains(key) == true } ) {
            Severity.Warn
        } else {
            Severity.Info
        }

    fun toDataFields(
        location: Location?,
        currentTime: KmpOffsetDateTime
    ): Map<String, DataFieldDescriptor> {
        val fields = mutableListOf<DataFieldDescriptor>()

        if (destination?.isNotBlank() == true) {
            val dest = if (destination.contains(">")) {
                val split = destination
                    .replace(" ", "")
                    .split(">")
                split
                    .joinToString(" > ") { code ->
                        PortRegistry.findPort(code)
                            ?.let { p -> "${p.name} (${p.country})"  }
                            ?: destination.capitalizeWords()
                    }
            } else {
                PortRegistry.findPort(destination)
                    ?.let { p -> "${p.name} (${p.country})"  }
                    ?: destination.capitalizeWords()
            }

            fields.add(DataFieldDescriptor(
                name = "destination",
                label = Res.string.label_destination,
                value = FieldValue(dest),
                wholeRow = true
            ))
        }
        fields.add(DataFieldDescriptor(
            name = "last_message",
            label = Res.string.label_last_message,
            value = FieldValue(currentTime.minus(timeUtc).formatTime()),
            wholeRow = true
        ))
        fields.add(DataFieldDescriptor(
            name = "distance",
            label = Res.string.label_distance,
            value = FieldValue(location?.let { l -> extrapolateDistance(currentTime, l).formatDistance() } ?: distance.formatDistance())
        ))
        if (!isMoored) {
            fields.add(DataFieldDescriptor(
                name = "speed_knots",
                label = Res.string.label_speed,
                value = FieldValue(sog, Res.string.label_unit_knots)
            ))
            fields.add(DataFieldDescriptor(
                name = "speed_kmh",
                label = Res.string.label_speed,
                value = FieldValue(speedKmh, Res.string.label_unit_kmh)
            ))
        }
        if (rateOfTurnDegreesPerMinute != 0.0) {
            fields.add(DataFieldDescriptor(
                name = "turnrate",
                label = Res.string.label_turnRate,
                value = FieldValue(rateOfTurnDegreesPerMinute.roundToInt(), Res.string.label_unit_degree_minute)
            ))
        }
        fields.add(DataFieldDescriptor(
            name = "mmsi",
            label = Res.string.label_mmsi,
            value = FieldValue(mmsi),
            href = "https://www.startpage.com/do/dsearch?query=mmsi%20${mmsi.toString().padStart(9, '0')}"
        ))
        if (imoNumber != null) {
            fields.add(DataFieldDescriptor(
                name = "imo",
                label = Res.string.label_imo,
                value = FieldValue(imoNumber),
                href = "https://www.startpage.com/do/dsearch?query=imo%20${imoNumber}"
            ))
        }
        if (callSign != null) {
            fields.add(DataFieldDescriptor(
                name = "callsign",
                label = Res.string.label_callsign,
                value = FieldValue(callSign),
                href = "https://www.startpage.com/do/dsearch?query=callsign%20${callSign}"
            ))
        }
        if (maximumStaticDraught != null) {
            fields.add(DataFieldDescriptor(
                name = "draught",
                label = Res.string.label_maxDraught,
                value = FieldValue(maximumStaticDraught, Res.string.label_unit_meters)
            ))
        }
        if (hasSafetyMessage) {
            fields.add(DataFieldDescriptor(
                name = "message",
                label = Res.string.label_message,
                value = FieldValue(decodedSafetyMessageText()),
                textColor = if (messageSeverity == Severity.Error) Color.White else TextColor,
                backgroundColor = messageSeverity.color(),
                wholeRow = true
            ))
            fields.add(DataFieldDescriptor(
                name = "location",
                label = Res.string.label_location,
                value = FieldValue(location?.toDmsString()),
                textColor = if (messageSeverity == Severity.Error) Color.White else TextColor,
                backgroundColor = messageSeverity.color(),
                wholeRow = true
            ))
        }

        return fields.associateBy { descriptor -> descriptor.name }
    }

    override fun toString(): String {
        return "AisDataUi(messageType=${messageType.name}, name='$name', mmsi=$mmsi, mmsiCountryPrefix=$mmsiCountryPrefix, timeUtc=$timeUtc, location=$location, isMoored=$isMoored, sog=$sog, speedKmh='$speedKmh', heading=$heading, rateOfTurnDegreesPerMinute=$rateOfTurnDegreesPerMinute, navigationalStatus=${navigationalStatus.name}, imoNumber=$imoNumber, callSign=$callSign, destination=$destination, totalLength=$totalLength, totalWidth=$totalWidth, shipType=${shipType.category.name}, maximumStaticDraught=$maximumStaticDraught, distance=$distance', hasSafetyMessage=$hasSafetyMessage, messageId=$messageId, repeatIndicator=$repeatIndicator, valid=$valid, text=$text, messageSeverity=$messageSeverity)"
    }

    fun toCsv(): String {
        return "${timeUtcObserved?.format("dd.MM.yyyy HH:mm:ss")};${observingLocation?.toDmsString()?:""};${shipType.category.name?:""};$name;$mmsi;${mmsiCountryPrefix.deviceType.name};${mmsiCountryPrefix.country.countryName};${callSign?:""};${imoNumber?:""};${messageType.name};$sog;$speedKmh;$heading;${destination?:""};${totalLength?:""};${totalWidth?:""};${maximumStaticDraught?:""};${location.toDmsString()};${distance.formatDistance()}"
    }

    fun decodedSafetyMessageText(): String {
        if (text == null) return ""

        val pob = (P_POB1.find(text)?.groups[1]?.value
            ?: P_POB2.find(text)?.groups[1]?.value
            ?: P_POB3.find(text)?.groups[1]?.value)
            ?.let { p ->"Persons on board: $p" }
            ?:""
        val ports = P_STRING.findAll(text)
            .map { m -> m.groups[1]?.value }
            .filter { s -> s?.length == 5 }
            .mapNotNull { s ->
                PortRegistry.findPort(s)
            }
            .joinToString(" - ") { port ->
                "${port.name} (${port.country})"
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

    fun extrapolateHeading(
        currentTime: KmpOffsetDateTime = KmpOffsetDateTime.now()
    ): Double {
        if (isMoored || rateOfTurnDegreesPerMinute == 0.0) return heading

        val framesElapsed = currentTime.minus(timeUtc).inWholeMilliseconds / 40.0

        if (framesElapsed > MAX_EXTRAPOLATION_FRAMES) return heading
        val rateOfTurnPerFrame = rateOfTurnDegreesPerMinute / 2400

        return heading + rateOfTurnPerFrame * framesElapsed
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
        if (isMoored) return location

        val framesElapsed = currentTime.minus(timeUtc).inWholeMilliseconds / 40.0

        if (framesElapsed > MAX_EXTRAPOLATION_FRAMES) return location

        val speedMetersPerMillsecond = sog * METERS_PER_FRAME
        val distanceTraveledMeters = (speedMetersPerMillsecond * framesElapsed).coerceAtMost(MAX_EXTRAPOLATION_DISTANCE_METERS)

//        val rateOfTurnPerFrame = rateOfTurnDegreesPerMinute / 2400
//        val courseRad = Math.toRadians(heading + rateOfTurnPerFrame * framesElapsed)
        val courseRad = Math.toRadians(heading)

        val deltaLat = (distanceTraveledMeters * cos(courseRad)) / RADIUS_EARTH_METERS
        val newLat = location.latitude + Math.toDegrees(deltaLat)

        val deltaLon =
            (distanceTraveledMeters * sin(courseRad)) / (RADIUS_EARTH_METERS * cos(Math.toRadians(location.latitude)))
        val newLon = location.longitude + Math.toDegrees(deltaLon)

        return Location(latitude = newLat, longitude = newLon)
    }

    fun extrapolateDistance(
        currentTime: KmpOffsetDateTime = KmpOffsetDateTime.now(),
        location: Location?
    ): Double {
        return location?.distanceTo(extrapolatedPosition(currentTime)) ?: 0.0
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
