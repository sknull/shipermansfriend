package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsIM {
    val PORTS: List<PortCode> = listOf(
        PortCode("IM", "CTN", "Castletown", Location(54.083333, -4.65)),
        PortCode("IM", "DGS", "Douglas", Location(54.166667, -4.483333)),
        PortCode("IM", "PEL", "Peel", Location(54.216667, -4.766667)),
        PortCode("IM", "PER", "Port Erin", Location(54.083333, -4.766667)),
        PortCode("IM", "PSM", "Port Saint Mary", Location(54.066667, -4.733333)),
        PortCode("IM", "RAM", "Ramsey", Location(54.316667, -4.383333)),
        PortCode("IM", "SAN", "Santon", Location(54.116667, -4.583333))
    )
}
