package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsGW {
    val PORTS: List<PortCode> = listOf(
        PortCode("GW", "BNT", "Binta", Location(12.416667, -15.316667)),
        PortCode("GW", "BOL", "Bolama", Location(11.583333, -15.466667)),
        PortCode("GW", "BQE", "Bubaque", Location(11.283333, -15.833333)),
        PortCode("GW", "CAC", "Cacheu", Location(12.266667, -16.15)),
        PortCode("GW", "CJA", "Canjaja", Location(12.383333, -15.35)),
        PortCode("GW", "FAR", "Farim", Location(12.483333, -15.216667)),
        PortCode("GW", "OXB", "Bissau", Location(11.85, -15.583333))
    )
}
