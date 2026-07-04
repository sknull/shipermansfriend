package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsSJ {
    val PORTS: List<PortCode> = listOf(
        PortCode("SJ", "BAR", "Barentsburg", Location(78.066667, 14.2)),
        PortCode("SJ", "LYR", "Longyearbyen", Location(78.216667, 15.633333)),
        PortCode("SJ", "NYA", "Ny-Ålesund", Location(78.933333, 11.95)),
        PortCode("SJ", "SVE", "Sveagruva", Location(77.85, 16.65))
    )
}
