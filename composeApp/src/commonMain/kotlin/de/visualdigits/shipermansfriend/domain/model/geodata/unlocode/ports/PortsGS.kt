package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsGS {
    val PORTS: List<PortCode> = listOf(
        PortCode("GS", "GRV", "Grytviken", Location(-54.266667, -36.5)),
        PortCode("GS", "LEH", "Leith Harbour", null)
    )
}
