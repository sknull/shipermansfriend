package de.visualdigits.shipermansfriend.domain.model.geodata.mmsi

import de.visualdigits.shipermansfriend.domain.model.geodata.mmsi.MmsiCountry.Companion.fromMid

interface MmsiPrefix {

    val prefix: String

    companion object {

        fun fromMmsi(mmsi: Long): MmsiCountryPrefix {
            val normalizedMmsi = mmsi.toString().padStart(9, '0')
            val deviceType = MmsiDeviceType.fromNormalizedMmsi(normalizedMmsi)
            val country = deviceType.extractMid(normalizedMmsi)
                ?.let { mid -> fromMid(mid)} ?: MmsiCountryEurope.COUNTRY_UNKNOWN

            return MmsiCountryPrefix(deviceType, country)
        }
    }
}
