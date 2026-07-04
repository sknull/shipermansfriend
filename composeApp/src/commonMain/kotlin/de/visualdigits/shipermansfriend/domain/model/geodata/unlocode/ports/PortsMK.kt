package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsMK {
    val PORTS: List<PortCode> = listOf(
        PortCode("MK", "DKJ", "Demir Kapija", Location(41.4, 22.233333)),
        PortCode("MK", "NOD", "Novi Dojran", Location(41.216667, 22.7)),
        PortCode("MK", "RAD", "Radovis", Location(41.65, 22.5)),
        PortCode("MK", "SNI", "Sveti Nikole", Location(41.85, 21.933333))
    )
}
