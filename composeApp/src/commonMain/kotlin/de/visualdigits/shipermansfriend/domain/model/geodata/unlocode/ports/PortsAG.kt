package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsAG {
    val PORTS: List<PortCode> = listOf(
        PortCode("AG", "ANU", "Antigua", null),
        PortCode("AG", "BBQ", "Barbuda", null),
        PortCode("AG", "FAM", "Falmouth", Location(17.016667, -61.783333)),
        PortCode("AG", "PHM", "Parham", Location(17.083333, -61.75)),
        PortCode("AG", "SJO", "Saint John's", Location(17.116667, -61.85))
    )
}
