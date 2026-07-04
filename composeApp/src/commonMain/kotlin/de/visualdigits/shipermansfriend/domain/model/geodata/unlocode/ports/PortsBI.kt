package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsBI {
    val PORTS: List<PortCode> = listOf(
        PortCode("BI", "CBK", "Cibitoke", Location(-3.333333, 29.366667))
    )
}
