package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsNU {
    val PORTS: List<PortCode> = listOf(
        PortCode("NU", "ALO", "Alofi", Location(-19.05, -169.916667)),
        PortCode("NU", "IUE", "Niue Island", null)
    )
}
