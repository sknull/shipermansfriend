package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsRW {
    val PORTS: List<PortCode> = listOf(
        PortCode("RW", "NSA", "Nyagatare", Location(-1.283333, 30.316667)),
        PortCode("RW", "RUB", "Rubengera", null),
        PortCode("RW", "RW4", "Rwamagana", Location(1.95, 30.433333))
    )
}
