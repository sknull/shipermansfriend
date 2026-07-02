package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsGQ {
    val PORTS: List<PortCode> = listOf(
        PortCode("GQ", "BSG", "Bata", null),
        PortCode("GQ", "BUL", "Butuku-Luba", null),
        PortCode("GQ", "COG", "Cogo", null),
        PortCode("GQ", "LUB", "Luba", null),
        PortCode("GQ", "PET", "Punta Europa Terminal", Location(3.783333, 8.716667)),
        PortCode("GQ", "SER", "Serpentina Terminal", Location(3.8, 8.083333)),
        PortCode("GQ", "SSG", "Malabo", null),
        PortCode("GQ", "ZAF", "Zafiro Terminal", Location(3.85, 8.166667))
    )
}
