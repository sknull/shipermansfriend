package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsSO {
    val PORTS: List<PortCode> = listOf(
        PortCode("SO", "BBO", "Berbera", null),
        PortCode("SO", "ELM", "El Maan", Location(2.166667, 45.6)),
        PortCode("SO", "GHR", "Giohar", Location(2.766667, 45.516667)),
        PortCode("SO", "GRC", "Garacad", Location(6.933333, 49.316667)),
        PortCode("SO", "KMU", "Kismayu", null),
        PortCode("SO", "MER", "Merca", null),
        PortCode("SO", "MGQ", "Mogadishu", null)
    )
}
