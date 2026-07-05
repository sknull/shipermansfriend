package de.visualdigits.shipermansfriend.domain.model.geodata.mmsi

import org.jetbrains.compose.resources.DrawableResource

interface MmsiCountry : MmsiPrefix {

    val countryCode: String

    val countryName: String

    val flag: DrawableResource

    companion object {

        fun fromMid(mid: String): MmsiCountry? {
            return MmsiCountryEurope.fromMid(mid)
                ?: MmsiCountryNorthAmerica.fromMid(mid)
                ?: MmsiCountryAsia.fromMid(mid)
                ?: MmsiCountryOceania.fromMid(mid)
                ?: MmsiCountryAfrica.fromMid(mid)
                ?: MmsiCountrySouthAmerica.fromMid(mid)
        }

        fun fromCountryCode(countryCode: String): MmsiCountry? {
            return MmsiCountryEurope.fromCountryCode(countryCode)
                ?: MmsiCountryNorthAmerica.fromCountryCode(countryCode)
                ?: MmsiCountryAsia.fromCountryCode(countryCode)
                ?: MmsiCountryOceania.fromCountryCode(countryCode)
                ?: MmsiCountryAfrica.fromCountryCode(countryCode)
                ?: MmsiCountrySouthAmerica.fromCountryCode(countryCode)
        }
    }
}
