package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsMC {
    val PORTS: List<PortCode> = listOf(
        PortCode("MC", "MCM", "Monte-Carlo", Location(43.733333, 7.416667)),
        PortCode("MC", "MON", "Monaco", null)
    )
}
