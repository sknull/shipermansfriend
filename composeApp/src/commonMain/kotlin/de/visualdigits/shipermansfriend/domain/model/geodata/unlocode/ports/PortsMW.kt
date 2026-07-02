package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsMW {
    val PORTS: List<PortCode> = listOf(
        PortCode("MW", "CPK", "Chipoka", Location(-14.0, 34.516667)),
        PortCode("MW", "THY", "Thyolo", Location(-16.066667, 35.133333))
    )
}
