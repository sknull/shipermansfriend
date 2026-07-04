package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsEH {
    val PORTS: List<PortCode> = listOf(
        PortCode("EH", "EAI", "Ejbei Uad el Aabd", Location(27.15, -13.2))
    )
}
