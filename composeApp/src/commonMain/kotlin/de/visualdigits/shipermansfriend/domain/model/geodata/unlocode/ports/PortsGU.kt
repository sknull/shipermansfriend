package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsGU {
    val PORTS: List<PortCode> = listOf(
        PortCode("GU", "APR", "Apra (Agana)", null),
        PortCode("GU", "BRR", "Barrigada", Location(13.466667, 144.783333)),
        PortCode("GU", "GUM", "Guam", null)
    )
}
