package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsCK {
    val PORTS: List<PortCode> = listOf(
        PortCode("CK", "AIT", "Aitutaki", null),
        PortCode("CK", "AIU", "Atiu", null),
        PortCode("CK", "ARU", "Arutunga", null),
        PortCode("CK", "MGS", "Mangaia", null),
        PortCode("CK", "MOI", "Mitiaro Island", null),
        PortCode("CK", "RAR", "Rarotonga", null)
    )
}
