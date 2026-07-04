package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsTT {
    val PORTS: List<PortCode> = listOf(
        PortCode("TT", "CHA", "Chaguaramas", null),
        PortCode("TT", "CHV", "Charlotteville", Location(11.316667, -60.55)),
        PortCode("TT", "CLA", "Claxton Bay", Location(10.35, -61.466667)),
        PortCode("TT", "CRN", "Carenage", Location(10.683333, -61.6)),
        PortCode("TT", "CVA", "Couva", Location(10.416667, -61.45)),
        PortCode("TT", "FPT", "Saint Mary's", Location(10.216667, -61.533333)),
        PortCode("TT", "FRE", "Freeport", Location(10.45, -61.416667)),
        PortCode("TT", "GUA", "Guayaguayare", Location(10.133333, -61.033333)),
        PortCode("TT", "LAB", "La Brea (Brighton)", null),
        PortCode("TT", "PLY", "Plymouth", null),
        PortCode("TT", "POS", "Port-of-Spain", Location(10.65, -61.516667)),
        PortCode("TT", "PTF", "Point Fortin", null),
        PortCode("TT", "PTG", "Point Galeota", null),
        PortCode("TT", "PTP", "Pointe a Pierre", null),
        PortCode("TT", "PTS", "Point Lisas", null),
        PortCode("TT", "SCA", "Scarborough/Tobago", null),
        PortCode("TT", "SFE", "San Fernando", null),
        PortCode("TT", "TEM", "Tembladora", null)
    )
}
