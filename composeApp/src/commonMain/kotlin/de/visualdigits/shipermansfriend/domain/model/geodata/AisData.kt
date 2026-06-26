package de.visualdigits.shipermansfriend.domain.model.geodata

import de.visualdigits.common.domain.model.common.KmpOffsetDateTime

abstract class AisData(
    val name: String,
    val mmsi: Long,
    val timeUtc: KmpOffsetDateTime
) {
    override fun toString(): String {
        return "AisData(name='$name', mmsi=$mmsi, timeUtc=$timeUtc)"
    }
}
