package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsCX {
    val PORTS: List<PortCode> = listOf(
        PortCode("CX", "FFC", "Flying Fish Cove", Location(-10.416667, 105.716667))
    )
}
