package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsYE {
    val PORTS: List<PortCode> = listOf(
        PortCode("YE", "ADE", "Aden", null),
        PortCode("YE", "AHW", "Ahwar", null),
        PortCode("YE", "ELK", "El Katieb", null),
        PortCode("YE", "HAR", "Harad", null),
        PortCode("YE", "HAU", "Haura", null),
        PortCode("YE", "HOD", "Hodeidah", Location(14.833333, 42.9)),
        PortCode("YE", "KHO", "Khokha", null),
        PortCode("YE", "LAD", "Little Aden", Location(12.733333, 44.866667)),
        PortCode("YE", "MKX", "Mukalla", null),
        PortCode("YE", "MOK", "Mokha", null),
        PortCode("YE", "MYN", "Mareb", null),
        PortCode("YE", "PRM", "Perim Island", Location(12.65, 43.416667)),
        PortCode("YE", "RAI", "Ras Isa Terminal", Location(15.2, 42.666667)),
        PortCode("YE", "RAK", "Ras Al Kalib", null),
        PortCode("YE", "SAL", "Saleef Port", null),
        PortCode("YE", "SCT", "Suqutrá", Location(12.5, 54.0)),
        PortCode("YE", "SYE", "Sadah", null)
    )
}
