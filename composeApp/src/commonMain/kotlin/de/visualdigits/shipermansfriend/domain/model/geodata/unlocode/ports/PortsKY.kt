package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsKY {
    val PORTS: List<PortCode> = listOf(
        PortCode("KY", "GEC", "Georgetown, Grand Cayman", Location(19.3, -81.383333))
    )
}
