package de.visualdigits.shipermansfriend.domain.model.geodata

import org.jetbrains.compose.resources.DrawableResource

interface MmsiCountry : MmsiPrefix {

    val countryCode: String

    val countryName: String

    val flag: DrawableResource
}
