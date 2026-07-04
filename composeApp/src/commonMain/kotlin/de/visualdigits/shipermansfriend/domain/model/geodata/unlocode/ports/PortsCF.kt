package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsCF {
    val PORTS: List<PortCode> = listOf(
        PortCode("CF", "TOG", "Togo", Location(5.5, 21.716667))
    )
}
