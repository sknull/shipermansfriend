package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsVG {
    val PORTS: List<PortCode> = listOf(
        PortCode("VG", "EIS", "Beef Island, Tortola", null),
        PortCode("VG", "JVD", "Jost Van Dyke", Location(18.45, -64.733333)),
        PortCode("VG", "NIS", "Norman Island", Location(18.316667, -64.616667)),
        PortCode("VG", "PUR", "Port Purcell", Location(18.416667, -64.6)),
        PortCode("VG", "RAD", "Road Town, Tortola", null),
        PortCode("VG", "SHO", "Sopers Hole", Location(18.383333, -64.7)),
        PortCode("VG", "TOV", "Tortola", null)
    )
}
