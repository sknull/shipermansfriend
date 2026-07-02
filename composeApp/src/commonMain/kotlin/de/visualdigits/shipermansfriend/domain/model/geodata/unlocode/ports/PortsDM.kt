package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsDM {
    val PORTS: List<PortCode> = listOf(
        PortCode("DM", "ADM", "Anse du Mai", Location(15.6, -61.383333)),
        PortCode("DM", "BEL", "Belfast", null),
        PortCode("DM", "POR", "Portsmouth", Location(15.566667, -61.45)),
        PortCode("DM", "RSU", "Roseau", null)
    )
}
