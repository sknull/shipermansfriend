package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsAF {
    val PORTS: List<PortCode> = listOf(
        PortCode("AF", "DHD", "Dehdadi", Location(36.65, 66.983333)),
        PortCode("AF", "QLT", "Qalat", Location(32.1, 66.883333)),
        PortCode("AF", "RKH", "Torkham", Location(34.883333, 71.916667))
    )
}
