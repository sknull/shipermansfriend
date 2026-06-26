package de.visualdigits.shipermansfriend.domain.model.geodata

import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location

class PositionData(
    name: String,
    mmsi: Long,
    timeUtc: KmpOffsetDateTime,
    val location: Location,
    val sog: Double,
    val heading: Double
) : AisData(
    name,
    mmsi,
    timeUtc
) {
    override fun toString(): String {
        return "PositionData(name='$name', mmsi=$mmsi, timeUtc=$timeUtc, location=$location, sog=$sog, heading=$heading)"
    }
}

