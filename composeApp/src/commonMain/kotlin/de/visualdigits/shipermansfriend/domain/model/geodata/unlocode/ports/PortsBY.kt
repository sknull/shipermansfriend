package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsBY {
    val PORTS: List<PortCode> = listOf(
        PortCode("BY", "KLK", "Kletsk", Location(53.05, 26.616667)),
        PortCode("BY", "KYC", "Kostyukovichi", Location(53.35, 32.05)),
        PortCode("BY", "PIK", "Pinsk", Location(52.116667, 26.083333)),
        PortCode("BY", "RYA", "Rechytsa", Location(52.35, 30.4)),
        PortCode("BY", "SNM", "Slonim", Location(53.1, 25.316667)),
        PortCode("BY", "VAW", "Vawkavysk", Location(53.15, 24.433333))
    )
}
