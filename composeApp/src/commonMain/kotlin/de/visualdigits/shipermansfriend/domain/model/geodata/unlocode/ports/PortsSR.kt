package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsSR {
    val PORTS: List<PortCode> = listOf(
        PortCode("SR", "ABN", "Albina", null),
        PortCode("SR", "AGI", "Wageningen", null),
        PortCode("SR", "ICK", "Nieuw Nickerie", null),
        PortCode("SR", "LLW", "Lelydorp", Location(5.7, -55.233333)),
        PortCode("SR", "MOJ", "Moengo", null),
        PortCode("SR", "PBM", "Paramaribo", null),
        PortCode("SR", "PRM", "Paranam", null),
        PortCode("SR", "SMA", "Smalkalden", null)
    )
}
