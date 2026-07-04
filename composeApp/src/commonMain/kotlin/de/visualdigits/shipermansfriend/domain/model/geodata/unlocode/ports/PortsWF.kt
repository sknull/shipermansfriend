package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsWF {
    val PORTS: List<PortCode> = listOf(
        PortCode("WF", "HLO", "Halalo", Location(-13.333333, -176.216667)),
        PortCode("WF", "MAU", "Matâ'utu", Location(-13.283333, -176.183333)),
        PortCode("WF", "SIG", "Sigavé", Location(-14.283333, -178.166667))
    )
}
