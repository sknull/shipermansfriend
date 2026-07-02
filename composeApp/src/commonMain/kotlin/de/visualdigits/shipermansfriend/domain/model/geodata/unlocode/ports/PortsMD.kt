package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsMD {
    val PORTS: List<PortCode> = listOf(
        PortCode("MD", "GIU", "Giurgiulesti", Location(45.466667, 28.183333)),
        PortCode("MD", "XXX", "RIS Inland waterways", null)
    )
}
