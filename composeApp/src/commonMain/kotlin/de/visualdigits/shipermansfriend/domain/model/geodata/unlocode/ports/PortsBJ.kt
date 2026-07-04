package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsBJ {
    val PORTS: List<PortCode> = listOf(
        PortCode("BJ", "COO", "Cotonou", null),
        PortCode("BJ", "PTN", "Porto-Novo", Location(6.483333, 2.616667)),
        PortCode("BJ", "SEM", "Seme Terminal", null)
    )
}
