package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsGA {
    val PORTS: List<PortCode> = listOf(
        PortCode("GA", "CCB", "Cocobeach", null),
        PortCode("GA", "CLZ", "Cap Lopez", null),
        PortCode("GA", "EKU", "Equata", Location(-0.216667, 9.3)),
        PortCode("GA", "GAX", "Gamba", null),
        PortCode("GA", "LBV", "Libreville", null),
        PortCode("GA", "LUC", "Lucina", null),
        PortCode("GA", "MBY", "M'bya Terminal", null),
        PortCode("GA", "NYA", "Nyanga", null),
        PortCode("GA", "OGU", "Oguandjo Terminal", Location(-1.5, 8.9)),
        PortCode("GA", "OWE", "Owendo", null),
        PortCode("GA", "POG", "Port Gentil", null)
    )
}
