package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsAW {
    val PORTS: List<PortCode> = listOf(
        PortCode("AW", "AUA", "Aruba", Location(12.5, -69.966667)),
        PortCode("AW", "BAR", "Barcadera", Location(12.483333, -69.983333)),
        PortCode("AW", "BUS", "Bushiribana", Location(12.55, -69.966667)),
        PortCode("AW", "DEU", "Druif", null),
        PortCode("AW", "ORJ", "Oranjestad", null),
        PortCode("AW", "SNL", "Sint Nicolaas", null)
    )
}
