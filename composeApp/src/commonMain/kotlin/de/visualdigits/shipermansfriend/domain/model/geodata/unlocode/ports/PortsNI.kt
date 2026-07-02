package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsNI {
    val PORTS: List<PortCode> = listOf(
        PortCode("NI", "8LG", "Totogalpa", Location(13.566667, -86.5)),
        PortCode("NI", "BEF", "Bluefields", null),
        PortCode("NI", "CIO", "Corinto", null),
        PortCode("NI", "ELB", "El Bluff", null),
        PortCode("NI", "LM8", "Las Maderas", Location(12.433333, -86.033333)),
        PortCode("NI", "MAC", "Masachapa", Location(11.783333, -86.516667)),
        PortCode("NI", "PIB", "Puerto Isabel", Location(13.366667, -83.566667)),
        PortCode("NI", "PRI", "Prinzapolca", null),
        PortCode("NI", "PSN", "Puerto Sandino", Location(12.183333, -86.75)),
        PortCode("NI", "PTI", "Potosí", Location(13.0, -87.5)),
        PortCode("NI", "PUZ", "Puerto Cabezas", null),
        PortCode("NI", "RAM", "Rama", Location(12.15, -84.216667)),
        PortCode("NI", "SJS", "San Juan del Sur", null),
        PortCode("NI", "TPA", "Tipitapa", Location(12.2, -86.1))
    )
}
