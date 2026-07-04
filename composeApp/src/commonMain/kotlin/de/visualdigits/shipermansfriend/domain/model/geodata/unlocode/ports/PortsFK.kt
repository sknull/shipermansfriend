package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsFK {
    val PORTS: List<PortCode> = listOf(
        PortCode("FK", "FBE", "Fox Bay", null),
        PortCode("FK", "PSY", "Port Stanley", null)
    )
}
