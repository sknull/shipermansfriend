package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsBZ {
    val PORTS: List<PortCode> = listOf(
        PortCode("BZ", "BAR", "Barranco", Location(16.0, -88.916667)),
        PortCode("BZ", "BGK", "Big Creek", Location(16.516667, -88.4)),
        PortCode("BZ", "BZE", "Belize City", null),
        PortCode("BZ", "COL", "Colinto", null),
        PortCode("BZ", "DGA", "Dangriga", Location(16.966667, -88.216667))
    )
}
