package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsMS {
    val PORTS: List<PortCode> = listOf(
        PortCode("MS", "LTB", "Little Bay", Location(16.8, -62.2)),
        PortCode("MS", "PLY", "Plymouth", null)
    )
}
