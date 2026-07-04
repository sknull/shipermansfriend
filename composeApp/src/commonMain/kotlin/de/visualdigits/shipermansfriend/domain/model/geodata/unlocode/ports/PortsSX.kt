package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsSX {
    val PORTS: List<PortCode> = listOf(
        PortCode("SX", "GES", "Gallis Bay", Location(18.016667, -63.066667)),
        PortCode("SX", "MAR", "Marigot", Location(18.066667, -63.066667)),
        PortCode("SX", "PHI", "Philipsburg", Location(18.016667, -63.033333)),
        PortCode("SX", "SXM", "Sint-Maarten Apt", Location(18.05, -63.116667))
    )
}
