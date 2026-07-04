package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsCC {
    val PORTS: List<PortCode> = listOf(
        PortCode("CC", "CCK", "Cocos Islands", Location(-12.0, 96.833333))
    )
}
