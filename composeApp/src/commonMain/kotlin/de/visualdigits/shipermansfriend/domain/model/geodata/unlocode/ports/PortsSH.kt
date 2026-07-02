package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsSH {
    val PORTS: List<PortCode> = listOf(
        PortCode("SH", "ASC", "Ascension", Location(-7.95, -14.35)),
        PortCode("SH", "ASI", "Georgetown", null),
        PortCode("SH", "RPT", "Rupert's Bay", Location(-15.916667, -5.7)),
        PortCode("SH", "SHN", "Jamestown", null),
        PortCode("SH", "TDC", "Tristan da Cunha", null)
    )
}
