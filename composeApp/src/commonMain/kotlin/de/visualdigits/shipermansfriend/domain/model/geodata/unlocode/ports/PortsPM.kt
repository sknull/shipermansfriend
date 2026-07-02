package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsPM {
    val PORTS: List<PortCode> = listOf(
        PortCode("PM", "FSP", "Saint-Pierre", Location(46.766667, -56.166667))
    )
}
