package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsLC {
    val PORTS: List<PortCode> = listOf(
        PortCode("LC", "CAS", "Castries", Location(14.016667, -60.966667)),
        PortCode("LC", "CDS", "Cul de Sac", null),
        PortCode("LC", "VIF", "Vieux Fort", null)
    )
}
