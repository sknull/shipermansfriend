package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsCV {
    val PORTS: List<PortCode> = listOf(
        PortCode("CV", "GRA", "Porto Grande", Location(16.866667, -25.0)),
        PortCode("CV", "MIN", "Mindelo", Location(16.883333, -25.0)),
        PortCode("CV", "RAI", "Praia", null),
        PortCode("CV", "VXE", "Sao Vicente", null)
    )
}
