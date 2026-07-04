package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsBN {
    val PORTS: List<PortCode> = listOf(
        PortCode("BN", "BWN", "Bandar Seri Begawan", Location(4.883333, 114.933333)),
        PortCode("BN", "KUB", "Kuala Belait", null),
        PortCode("BN", "LUM", "Lumut", null),
        PortCode("BN", "MUA", "Muara", null),
        PortCode("BN", "SER", "Seria", null),
        PortCode("BN", "TAS", "Tanjong Selirong", Location(4.9, 115.116667))
    )
}
