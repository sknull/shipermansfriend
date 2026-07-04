package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsMH {
    val PORTS: List<PortCode> = listOf(
        PortCode("MH", "MAJ", "Majuro", Location(7.1, 171.383333)),
        PortCode("MH", "TAR", "Taroa", null)
    )
}
