package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsNA {
    val PORTS: List<PortCode> = listOf(
        PortCode("NA", "LUD", "Lüderitz", Location(-26.65, 15.166667)),
        PortCode("NA", "RUA", "Ruacana", Location(-17.433333, 14.35)),
        PortCode("NA", "WVB", "Walvis Bay", null)
    )
}
