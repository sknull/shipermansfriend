package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsST {
    val PORTS: List<PortCode> = listOf(
        PortCode("ST", "PCP", "Principe", null),
        PortCode("ST", "SAA", "Santo Antonio", null),
        PortCode("ST", "TMS", "Sao Tome Island", null)
    )
}
