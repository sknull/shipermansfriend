package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsGG {
    val PORTS: List<PortCode> = listOf(
        PortCode("GG", "ACI", "Alderney", Location(49.683333, -2.2)),
        PortCode("GG", "GCI", "Guernsey", Location(49.433333, -2.583333)),
        PortCode("GG", "SPT", "Saint Peter Port", Location(49.45, -2.55))
    )
}
