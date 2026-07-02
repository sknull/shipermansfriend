package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode

import de.visualdigits.common.domain.model.geodata.Location

data class PortCode(
    val country: String,
    val code: String,
    val name: String,
    val location: Location? = null
)
