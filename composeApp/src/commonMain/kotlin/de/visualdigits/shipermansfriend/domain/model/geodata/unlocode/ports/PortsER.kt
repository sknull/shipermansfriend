package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsER {
    val PORTS: List<PortCode> = listOf(
        PortCode("ER", "ASA", "Assab", Location(13.0, 42.733333)),
        PortCode("ER", "MSW", "Massawa (Mitsiwa)", Location(15.6, 39.45))
    )
}
