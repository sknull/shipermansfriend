package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsGM {
    val PORTS: List<PortCode> = listOf(
        PortCode("GM", "BJL", "Banjul", null),
        PortCode("GM", "SUK", "Sukuta", Location(13.4, -16.7))
    )
}
