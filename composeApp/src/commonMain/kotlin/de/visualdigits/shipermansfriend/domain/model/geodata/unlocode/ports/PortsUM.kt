package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsUM {
    val PORTS: List<PortCode> = listOf(
        PortCode("UM", "AWK", "Wake Island", null),
        PortCode("UM", "JON", "Johnston Atoll", null),
        PortCode("UM", "MDY", "Midway Islands", null)
    )
}
