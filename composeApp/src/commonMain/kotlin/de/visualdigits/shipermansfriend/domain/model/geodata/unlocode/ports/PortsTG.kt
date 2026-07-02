package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsTG {
    val PORTS: List<PortCode> = listOf(
        PortCode("TG", "ANE", "Aného", Location(6.233333, 1.6)),
        PortCode("TG", "KPE", "Kpeme", null),
        PortCode("TG", "LFW", "Lome", null)
    )
}
