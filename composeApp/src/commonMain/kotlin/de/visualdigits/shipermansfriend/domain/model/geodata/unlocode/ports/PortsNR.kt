package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsNR {
    val PORTS: List<PortCode> = listOf(
        PortCode("NR", "INU", "Nauru Island", Location(-0.55, 166.916667))
    )
}
