package de.visualdigits.shipermansfriend.domain.model.geodata

import de.visualdigits.shipermansfriend.domain.model.serializer.FixTypeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = FixTypeSerializer::class)
enum class FixType {
    UNDEFINED,
    GPS,
    GLONASS,
    COMBINED_GPS_GLONASS,
    LORAN_C,
    CHAYKA,
    INTEGRATED_NAVIGATION_SYSTEM,
    SURVEYED,
    GALILEO,
    UNUSED_0,
    UNUSED_1,
    UNUSED_2,
    UNUSED_3,
    UNUSED_4,
    UNUSED_5,
    INTERNAL
    ;

    companion object {
        fun fromOrdinal(ordinal: Int): FixType? = FixType.entries.find { it.ordinal == ordinal }
    }
}
