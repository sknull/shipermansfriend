package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsAX {
    val PORTS: List<PortCode> = listOf(
        PortCode("AX", "MHQ", "Maarianhamina (Mariehamn)", Location(60.1, 19.95)),
        PortCode("AX", "MHQ", "Mariehamn (Maarianhamina)", Location(60.1, 19.95))
    )
}
