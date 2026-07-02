package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsSN {
    val PORTS: List<PortCode> = listOf(
        PortCode("SN", "DKR", "Dakar", null),
        PortCode("SN", "FOU", "Foundiougne", null),
        PortCode("SN", "KLC", "Kaolack", null),
        PortCode("SN", "LYN", "Lyndiane", null),
        PortCode("SN", "MBA", "M'bao Terminal", null),
        PortCode("SN", "XLS", "Saint Louis", Location(16.033333, -16.5)),
        PortCode("SN", "ZIG", "Ziguinchor", null)
    )
}
