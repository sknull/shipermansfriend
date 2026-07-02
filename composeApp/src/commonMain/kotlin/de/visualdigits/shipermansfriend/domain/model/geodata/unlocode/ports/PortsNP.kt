package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsNP {
    val PORTS: List<PortCode> = listOf(
        PortCode("NP", "LLU", "Nawalparasi", Location(27.533333, 83.666667))
    )
}
