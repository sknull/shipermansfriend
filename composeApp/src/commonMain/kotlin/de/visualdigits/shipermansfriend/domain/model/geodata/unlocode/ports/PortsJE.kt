package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsJE {
    val PORTS: List<PortCode> = listOf(
        PortCode("JE", "SAB", "Saint Aubin", Location(49.183333, -2.166667)),
        PortCode("JE", "SCJ", "Saint Clement", Location(49.166667, -2.066667)),
        PortCode("JE", "STH", "Saint Hélier", Location(49.183333, -2.1))
    )
}
