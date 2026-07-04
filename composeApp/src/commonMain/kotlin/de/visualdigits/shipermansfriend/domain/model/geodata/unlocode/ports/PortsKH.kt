package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsKH {
    val PORTS: List<PortCode> = listOf(
        PortCode("KH", "CHE", "Cheko", null),
        PortCode("KH", "KKO", "Kas Kong", null),
        PortCode("KH", "KOS", "Kâmpóng Saôm", Location(10.6, 103.516667)),
        PortCode("KH", "PNH", "Phnom Penh", null),
        PortCode("KH", "TKH", "Ta Khmau", Location(11.483333, 104.95))
    )
}
