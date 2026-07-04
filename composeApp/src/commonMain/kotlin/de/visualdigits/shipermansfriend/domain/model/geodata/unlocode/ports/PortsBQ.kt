package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsBQ {
    val PORTS: List<PortCode> = listOf(
        PortCode("BQ", "BON", "Bonaire", Location(12.116667, -68.266667)),
        PortCode("BQ", "EUX", "Sint Eustatius", Location(17.483333, -62.966667)),
        PortCode("BQ", "GOT", "Goto", Location(12.216667, -68.383333)),
        PortCode("BQ", "KRA", "Kralendijk", Location(12.15, -68.266667)),
        PortCode("BQ", "RIN", "Dorp Rincón", Location(12.233333, -68.333333)),
        PortCode("BQ", "SAB", "Saba", Location(17.633333, -63.233333))
    )
}
