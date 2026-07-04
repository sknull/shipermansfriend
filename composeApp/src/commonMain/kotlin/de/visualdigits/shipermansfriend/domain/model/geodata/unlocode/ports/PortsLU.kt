package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsLU {
    val PORTS: List<PortCode> = listOf(
        PortCode("LU", "BKM", "Bech-Kleinmacher", Location(49.533333, 6.35)),
        PortCode("LU", "EZT", "Esch-sur-Alzette", Location(49.5, 5.983333)),
        PortCode("LU", "HDK", "Huldange", Location(50.15, 6.0)),
        PortCode("LU", "LUX", "Luxembourg", null),
        PortCode("LU", "MRT", "Mertert", Location(49.7, 6.483333)),
        PortCode("LU", "ROD", "Rodange", null),
        PortCode("LU", "WLG", "Wormeldange", Location(49.6, 6.4)),
        PortCode("LU", "XXX", "RIS Inland waterways", null),
        PortCode("LU", "ZXC", "Grundhof", Location(49.833333, 6.316667))
    )
}
