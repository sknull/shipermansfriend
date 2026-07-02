package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsHM {
    val PORTS: List<PortCode> = listOf(
        PortCode("HM", "HEA", "Heard Island", Location(-53.083333, 73.716667)),
        PortCode("HM", "MCD", "McDonald Island", Location(-53.033333, 72.583333))
    )
}
