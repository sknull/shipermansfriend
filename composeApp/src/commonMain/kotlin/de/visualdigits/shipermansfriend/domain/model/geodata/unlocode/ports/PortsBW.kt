package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsBW {
    val PORTS: List<PortCode> = listOf(
        PortCode("BW", "DUK", "Dukwi", Location(-20.583333, 26.533333)),
        PortCode("BW", "MAH", "Mahalapye", Location(-23.1, 26.833333))
    )
}
