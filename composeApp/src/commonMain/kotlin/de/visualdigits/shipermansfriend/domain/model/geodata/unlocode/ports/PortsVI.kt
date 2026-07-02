package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsVI {
    val PORTS: List<PortCode> = listOf(
        PortCode("VI", "CHA", "Charlotte Amalie, Saint Thomas", null),
        PortCode("VI", "CTD", "Christiansted, Saint Croix", Location(17.75, -64.75)),
        PortCode("VI", "CZB", "Cruz Bay, Saint John", Location(18.333333, -64.8)),
        PortCode("VI", "ENP", "Enighed Pond", null),
        PortCode("VI", "FRD", "Frederiksted, Saint Croix", null),
        PortCode("VI", "HOC", "Hovic", null),
        PortCode("VI", "LIB", "Limetree Bay", null),
        PortCode("VI", "PAX", "Port Alucroix", Location(17.7, -64.766667)),
        PortCode("VI", "STT", "Saint Thomas", Location(18.35, -64.933333))
    )
}
