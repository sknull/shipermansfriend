package de.visualdigits.shipermansfriend.domain.model.geodata

import androidx.compose.runtime.Immutable
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi.Companion.MAX_EXTRAPOLATION_DISTANCE_METERS
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi.Companion.MAX_EXTRAPOLATION_FRAMES
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi.Companion.METERS_PER_FRAME
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi.Companion.RADIUS_EARTH_METERS
import kotlin.math.cos
import kotlin.math.sin

@Immutable
class PositionData(
    messageType: MessageType,
    name: String,
    mmsi: Long,
    timeUtc: KmpOffsetDateTime,
    val location: Location,
    val sog: Double,
    val heading: Double,
    val rateOfTurnDegreesPerMinute: Double,
    val navigationalStatus: NavigationalStatus
) : AisData(
    messageType,
    name,
    mmsi,
    timeUtc
) {
    override fun toString(): String {
        return "PositionData(messageType='$messageType', name='$name', mmsi=$mmsi, timeUtc=$timeUtc, location=$location, sog=$sog, heading=$heading)"
    }

    val isMoored: Boolean
        get() = sog < 0.5 || navigationalStatus == NavigationalStatus.MOORED

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
        location: Location
    ): Double {
        return location.distanceTo(extrapolatedPosition(currentTime))
    }
}

