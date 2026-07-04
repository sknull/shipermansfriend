package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsVU {
    val PORTS: List<PortCode> = listOf(
        PortCode("VU", "LUG", "Luganville", Location(-15.533333, 167.166667)),
        PortCode("VU", "PSA", "Port Sandwich", null),
        PortCode("VU", "SAN", "Santo", null),
        PortCode("VU", "VLI", "Port Vila", null)
    )
}
