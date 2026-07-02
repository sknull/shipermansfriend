package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsGD {
    val PORTS: List<PortCode> = listOf(
        PortCode("GD", "GRE", "Grenville", null),
        PortCode("GD", "HIL", "Hillsborough, Carriacou Is", null),
        PortCode("GD", "STG", "Saint George's", null)
    )
}
