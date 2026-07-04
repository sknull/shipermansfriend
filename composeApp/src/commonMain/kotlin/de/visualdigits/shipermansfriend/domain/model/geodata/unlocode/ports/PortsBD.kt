package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsBD {
    val PORTS: List<PortCode> = listOf(
        PortCode("BD", "ASJ", "Ashuganj", Location(24.033333, 91.016667)),
        PortCode("BD", "BNP", "Benapole", Location(23.05, 88.9)),
        PortCode("BD", "BZL", "Barisal", null),
        PortCode("BD", "CGP", "Chattogram", Location(22.333333, 91.833333)),
        PortCode("BD", "CHL", "Chalna", null),
        PortCode("BD", "KHL", "Khulna", null),
        PortCode("BD", "MGL", "Mongla", null),
        PortCode("BD", "MUN", "Munshiganj", Location(23.55, 90.533333)),
        PortCode("BD", "NAR", "Narayanganj", null),
        PortCode("BD", "PAY", "Payra Port", Location(21.933333, 90.266667)),
        PortCode("BD", "PCR", "Panchashar", Location(23.566667, 90.5)),
        PortCode("BD", "PGN", "Pangaon", Location(23.65, 90.45))
    )
}
