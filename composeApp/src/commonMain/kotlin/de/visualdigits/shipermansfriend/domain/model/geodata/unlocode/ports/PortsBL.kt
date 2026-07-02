package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsBL {
    val PORTS: List<PortCode> = listOf(
        PortCode("BL", "SBH", "Saint-Barthélemy", Location(17.9, -62.85))
    )
}
