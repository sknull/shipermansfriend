package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsUZ {
    val PORTS: List<PortCode> = listOf(
        PortCode("UZ", "AKT", "Akaltyn", Location(41.433333, 61.066667)),
        PortCode("UZ", "ASA", "Asaka", Location(40.633333, 72.233333))
    )
}
