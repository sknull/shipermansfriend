package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsPW {
    val PORTS: List<PortCode> = listOf(
        PortCode("PW", "ANG", "Angaur", null),
        PortCode("PW", "ROR", "Koror", null)
    )
}
