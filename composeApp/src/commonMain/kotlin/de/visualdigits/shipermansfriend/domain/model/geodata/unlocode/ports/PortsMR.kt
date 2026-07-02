package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsMR {
    val PORTS: List<PortCode> = listOf(
        PortCode("MR", "NDB", "Nouadhibou", null),
        PortCode("MR", "NKC", "Nouakchott", null),
        PortCode("MR", "PCE", "Point Central", null),
        PortCode("MR", "ROS", "Rosso", Location(16.5, -15.916667))
    )
}
