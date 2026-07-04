package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsMF {
    val PORTS: List<PortCode> = listOf(
        PortCode("MF", "GES", "Galisbay", Location(18.066667, -63.083333)),
        PortCode("MF", "MAR", "Marigot", Location(18.066667, -63.083333))
    )
}
