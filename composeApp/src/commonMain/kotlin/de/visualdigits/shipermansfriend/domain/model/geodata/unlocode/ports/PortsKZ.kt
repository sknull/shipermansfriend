package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsKZ {
    val PORTS: List<PortCode> = listOf(
        PortCode("KZ", "AAU", "Aktau", Location(43.65, 51.15)),
        PortCode("KZ", "BTN", "Bautino", Location(44.533333, 50.25)),
        PortCode("KZ", "TAU", "Temirtau", Location(50.05, 72.95))
    )
}
