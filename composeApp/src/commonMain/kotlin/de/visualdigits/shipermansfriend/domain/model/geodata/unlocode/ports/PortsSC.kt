package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsSC {
    val PORTS: List<PortCode> = listOf(
        PortCode("SC", "MAW", "Mahe", null),
        PortCode("SC", "POV", "Port Victoria", null),
        PortCode("SC", "VIC", "Victoria", Location(-4.616667, 55.45))
    )
}
