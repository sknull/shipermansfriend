package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsTK {
    val PORTS: List<PortCode> = listOf(
        PortCode("TK", "AFU", "Atafu", Location(-8.533333, -172.516667)),
        PortCode("TK", "FKO", "Fakaofo", Location(-9.366667, -171.25)),
        PortCode("TK", "NKU", "Nukunonu", Location(-9.166667, -171.85))
    )
}
