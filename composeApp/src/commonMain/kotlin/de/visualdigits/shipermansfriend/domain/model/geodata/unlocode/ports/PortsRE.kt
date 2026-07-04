package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsRE {
    val PORTS: List<PortCode> = listOf(
        PortCode("RE", "LEU", "Saint-Leu", null),
        PortCode("RE", "LPT", "Le Port", Location(-20.933333, 55.316667)),
        PortCode("RE", "POS", "Possession", Location(-20.933333, 55.333333)),
        PortCode("RE", "RSE", "Sainte-Rose", Location(-21.133333, 55.8)),
        PortCode("RE", "RUN", "Saint-Denis", Location(-20.9, 55.516667)),
        PortCode("RE", "SPL", "Saint-Paul", Location(-20.983333, 55.266667)),
        PortCode("RE", "SSZ", "Sainte-Suzanne", Location(-20.9, 55.6))
    )
}
