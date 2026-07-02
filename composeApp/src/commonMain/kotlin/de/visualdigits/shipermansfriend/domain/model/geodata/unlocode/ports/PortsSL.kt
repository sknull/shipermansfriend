package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsSL {
    val PORTS: List<PortCode> = listOf(
        PortCode("SL", "BTE", "Bonthe", null),
        PortCode("SL", "FNA", "Freetown", null),
        PortCode("SL", "KSY", "Kissy", Location(8.466667, -13.183333)),
        PortCode("SL", "NIT", "Nitti", null),
        PortCode("SL", "PEP", "Pepel", null),
        PortCode("SL", "SBO", "Sherbro", null)
    )
}
