package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsAS {
    val PORTS: List<PortCode> = listOf(
        PortCode("AS", "PPG", "Pago Pago", Location(14.266667, -170.7))
    )
}
