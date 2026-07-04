package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsMO {
    val PORTS: List<PortCode> = listOf(
        PortCode("MO", "MFM", "Macau", Location(22.2, 113.533333))
    )
}
