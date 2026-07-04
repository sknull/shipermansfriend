package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsCM {
    val PORTS: List<PortCode> = listOf(
        PortCode("CM", "DLA", "Douala", null),
        PortCode("CM", "GOU", "Garoua", Location(9.3, 13.4)),
        PortCode("CM", "KBI", "Kribi", null),
        PortCode("CM", "KOL", "Kole Terminal", null),
        PortCode("CM", "KUM", "Kumba", Location(4.633333, 9.433333)),
        PortCode("CM", "LIM", "Limbe", Location(4.016667, 9.216667)),
        PortCode("CM", "LIT", "Limboh Terminal", null),
        PortCode("CM", "MOU", "Moudi Terminal", Location(4.116667, 8.483333)),
        PortCode("CM", "TKC", "Tiko", null)
    )
}
