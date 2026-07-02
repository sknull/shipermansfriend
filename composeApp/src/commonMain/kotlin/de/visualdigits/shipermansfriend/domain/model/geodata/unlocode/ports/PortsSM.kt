package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsSM {
    val PORTS: List<PortCode> = listOf(
        PortCode("SM", "AQ8", "Gualdicciolo", Location(43.95, 12.4))
    )
}
