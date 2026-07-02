package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsTC {
    val PORTS: List<PortCode> = listOf(
        PortCode("TC", "9CB", "Cockburn Harbour", Location(21.483333, -71.533333)),
        PortCode("TC", "GDT", "Grand Turk Island", null),
        PortCode("TC", "NCA", "North Caicos", null),
        PortCode("TC", "PLS", "Providenciales", null),
        PortCode("TC", "SLX", "Salt Cay", null),
        PortCode("TC", "XSC", "South Caicos", null)
    )
}
