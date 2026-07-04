package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsUG {
    val PORTS: List<PortCode> = listOf(
        PortCode("UG", "JIN", "Jinja", null),
        PortCode("UG", "MKN", "Galiraya", Location(1.3, 32.866667)),
        PortCode("UG", "YMB", "Yumbe", null)
    )
}
