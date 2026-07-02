package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsWS {
    val PORTS: List<PortCode> = listOf(
        PortCode("WS", "AAU", "Asau", null),
        PortCode("WS", "APW", "Apia", null)
    )
}
