package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsZM {
    val PORTS: List<PortCode> = listOf(
        PortCode("ZM", "SIA", "Siavonga", Location(-16.533333, 28.716667))
    )
}
