package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsKN {
    val PORTS: List<PortCode> = listOf(
        PortCode("KN", "BAS", "Basseterre, Saint Kitts", null),
        PortCode("KN", "CHA", "Charlestown", Location(17.133333, -62.616667)),
        PortCode("KN", "NEV", "Nevis", Location(17.15, -62.583333))
    )
}
