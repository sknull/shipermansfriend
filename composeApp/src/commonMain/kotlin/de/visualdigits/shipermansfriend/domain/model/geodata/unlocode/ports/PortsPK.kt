package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsPK {
    val PORTS: List<PortCode> = listOf(
        PortCode("PK", "BQM", "Muhammad Bin Qasim/Karachi", Location(24.766667, 67.333333)),
        PortCode("PK", "GWD", "Gwadar", null),
        PortCode("PK", "JIW", "Jiwani", null),
        PortCode("PK", "KBA", "Keti Bandar", Location(24.133333, 67.433333)),
        PortCode("PK", "KBU", "Keti Bunder", null),
        PortCode("PK", "KCT", "Karachi Container Terminal", Location(24.833333, 66.966667)),
        PortCode("PK", "KHI", "Karachi", null),
        PortCode("PK", "KIA", "Kiamari", Location(24.816667, 66.966667)),
        PortCode("PK", "ORW", "Ormara", null),
        PortCode("PK", "PCT", "Pakistan International Container Terminal/Karachi", null),
        PortCode("PK", "PSI", "Pasni", null),
        PortCode("PK", "QCT", "Qasim International Container Terminal/Karachi", null)
    )
}
