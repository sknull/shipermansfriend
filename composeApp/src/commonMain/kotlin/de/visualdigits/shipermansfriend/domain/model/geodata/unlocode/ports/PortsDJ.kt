package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsDJ {
    val PORTS: List<PortCode> = listOf(
        PortCode("DJ", "DCT", "Doraleh Container Terminal", Location(11.6, 44.6)),
        PortCode("DJ", "DMJ", "Damerjog", Location(11.483333, 43.183333)),
        PortCode("DJ", "JIB", "Djibouti", null),
        PortCode("DJ", "POD", "Djibouti", Location(11.583333, 43.133333))
    )
}
