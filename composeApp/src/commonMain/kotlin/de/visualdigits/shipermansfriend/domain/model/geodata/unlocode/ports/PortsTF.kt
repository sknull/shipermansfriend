package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsTF {
    val PORTS: List<PortCode> = listOf(
        PortCode("TF", "PFR", "Port-aux-Français", Location(-49.333333, 70.216667))
    )
}
