package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsCG {
    val PORTS: List<PortCode> = listOf(
        PortCode("CG", "DJE", "Djeno Terminal", null),
        PortCode("CG", "MOS", "Mossaka", Location(-1.216667, 16.8)),
        PortCode("CG", "NKO", "N'Kossa Terminal", Location(-5.233333, 11.6)),
        PortCode("CG", "OUE", "Ouesso", null),
        PortCode("CG", "OYO", "Oyo", Location(1.0, 15.9)),
        PortCode("CG", "PNR", "Pointe Noire", Location(-4.8, 11.85)),
        PortCode("CG", "YOM", "Yombo", Location(-2.933333, 12.7))
    )
}
