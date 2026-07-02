package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsTL {
    val PORTS: List<PortCode> = listOf(
        PortCode("TL", "DIL", "Dili", Location(-8.55, 125.566667))
    )
}
