package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsCW {
    val PORTS: List<PortCode> = listOf(
        PortCode("CW", "BUB", "Bullenbaai", Location(12.166667, -69.0)),
        PortCode("CW", "CRB", "Caracas Baai", Location(12.066667, -68.85)),
        PortCode("CW", "CUR", "Curaçao", Location(12.166667, -69.0)),
        PortCode("CW", "FUI", "Fuikbaai", Location(12.05, -68.816667)),
        PortCode("CW", "SMB", "Sint Michielsbaai", Location(12.15, -68.983333)),
        PortCode("CW", "WIL", "Willemstad", Location(12.1, -68.916667))
    )
}
