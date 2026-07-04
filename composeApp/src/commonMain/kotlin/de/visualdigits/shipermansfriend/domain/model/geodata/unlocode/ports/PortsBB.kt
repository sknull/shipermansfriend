package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsBB {
    val PORTS: List<PortCode> = listOf(
        PortCode("BB", "BGI", "Bridgetown", null),
        PortCode("BB", "STM", "Saint Michael", Location(13.116667, -59.6))
    )
}
