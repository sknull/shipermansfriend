package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsSD {
    val PORTS: List<PortCode> = listOf(
        PortCode("SD", "MBH", "Marsa Bashayer", Location(19.383333, 37.3)),
        PortCode("SD", "PZU", "Port Sudan", null),
        PortCode("SD", "SWA", "Swakin", null)
    )
}
