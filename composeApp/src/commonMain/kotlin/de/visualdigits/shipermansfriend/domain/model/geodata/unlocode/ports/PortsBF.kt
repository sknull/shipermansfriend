package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsBF {
    val PORTS: List<PortCode> = listOf(
        PortCode("BF", "OB2", "Ziniaré", Location(12.583333, -1.3))
    )
}
