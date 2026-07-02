package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsJO {
    val PORTS: List<PortCode> = listOf(
        PortCode("JO", "AQJ", "Al 'Aqabah", Location(29.533333, 35.0)),
        PortCode("JO", "DAH", "Abu Alandah", Location(31.9, 35.966667))
    )
}
