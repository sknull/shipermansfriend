package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsGY {
    val PORTS: List<PortCode> = listOf(
        PortCode("GY", "ANT", "Adventure", Location(7.083333, -58.483333)),
        PortCode("GY", "EVR", "Everton", Location(6.916667, -58.333333)),
        PortCode("GY", "GEO", "Georgetown", null),
        PortCode("GY", "GFO", "Bartica", null),
        PortCode("GY", "LDN", "Linden", Location(6.0, -58.283333)),
        PortCode("GY", "NAM", "New Amsterdam", null)
    )
}
