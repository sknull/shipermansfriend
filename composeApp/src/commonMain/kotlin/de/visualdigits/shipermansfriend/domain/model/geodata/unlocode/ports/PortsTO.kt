package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsTO {
    val PORTS: List<PortCode> = listOf(
        PortCode("TO", "HPA", "Ha'apai", null),
        PortCode("TO", "NEI", "Neiafu", null),
        PortCode("TO", "PAN", "Pangai", null),
        PortCode("TO", "TBU", "Nuku'alofa", Location(-21.133333, -175.2))
    )
}
