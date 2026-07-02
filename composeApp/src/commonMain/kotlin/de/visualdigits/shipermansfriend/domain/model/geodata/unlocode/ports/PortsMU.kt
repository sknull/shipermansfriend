package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsMU {
    val PORTS: List<PortCode> = listOf(
        PortCode("MU", "ABN", "Albion", Location(-20.2, 57.4)),
        PortCode("MU", "BAM", "Bambous", null),
        PortCode("MU", "CUR", "Curepipe", null),
        PortCode("MU", "PLU", "Port Louis", null),
        PortCode("MU", "PMA", "Port Mathurin", Location(-19.683333, 63.416667)),
        PortCode("MU", "TAM", "Tamarin", Location(-20.316667, 57.366667))
    )
}
