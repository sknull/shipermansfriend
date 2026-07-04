package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsAI {
    val PORTS: List<PortCode> = listOf(
        PortCode("AI", "BLP", "Blowing Point", null),
        PortCode("AI", "FOR", "The Forest", null),
        PortCode("AI", "MBB", "Meads Bay Beach", Location(18.166667, -63.133333)),
        PortCode("AI", "RBY", "Road Bay", Location(18.2, -63.083333)),
        PortCode("AI", "ROA", "The Road", null),
        PortCode("AI", "SOM", "Sombrero", Location(18.6, -63.466667)),
        PortCode("AI", "WLL", "Wall Blake", null)
    )
}
