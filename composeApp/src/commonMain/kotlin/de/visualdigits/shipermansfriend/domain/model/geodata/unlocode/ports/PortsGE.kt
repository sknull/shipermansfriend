package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsGE {
    val PORTS: List<PortCode> = listOf(
        PortCode("GE", "ABK", "Sukhumi", Location(43.0, 41.0)),
        PortCode("GE", "ANA", "Anaklia", Location(42.4, 41.583333)),
        PortCode("GE", "BUS", "Batumi", null),
        PortCode("GE", "IES", "Khashmi", Location(41.75, 45.166667)),
        PortCode("GE", "KUL", "Kulevi", Location(42.266667, 41.633333)),
        PortCode("GE", "PTI", "Poti", null),
        PortCode("GE", "SPS", "Sup'sa", Location(42.033333, 41.8))
    )
}
