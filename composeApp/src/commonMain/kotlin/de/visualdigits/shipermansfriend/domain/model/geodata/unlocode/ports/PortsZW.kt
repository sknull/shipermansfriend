package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsZW {
    val PORTS: List<PortCode> = listOf(
        PortCode("ZW", "CHE", "Chegutu", Location(-18.133333, 30.15)),
        PortCode("ZW", "FLU", "Filabusi", Location(-20.533333, 29.283333)),
        PortCode("ZW", "GWA", "Gwanda", Location(-20.933333, 29.0)),
        PortCode("ZW", "RSP", "Rusape", Location(-18.533333, 32.116667)),
        PortCode("ZW", "ZMZ", "Zimbabwe", Location(-20.266667, 30.916667))
    )
}
