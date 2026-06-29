package de.visualdigits.shipermansfriend.domain.model.geodata

interface MmsiPrefix {

    val prefix: String

    companion object {

        fun fromMmsi(mmsi: Long): MmsiCountryPrefix {
            val normalizedMmsi = mmsi.toString().padStart(9, '0')
            val deviceType = MmsiPrefixReserved.fromNormalizedMmsi(normalizedMmsi)
            val country = deviceType.extractMid(normalizedMmsi)?.let { mid ->
                MmsiCountryEurope.fromMid(mid)
                    ?: MmsiCountryNorthAmerica.fromMid(mid)
                    ?: MmsiCountryAsia.fromMid(mid)
                    ?: MmsiCountryOceania.fromMid(mid)
                    ?: MmsiCountryAfrica.fromMid(mid)
                    ?: MmsiCountrySouthAmerica.fromMid(mid)
            } ?: MmsiCountryEurope.COUNTRY_UNKNOWN

            return MmsiCountryPrefix(deviceType, country)
        }
    }
}
