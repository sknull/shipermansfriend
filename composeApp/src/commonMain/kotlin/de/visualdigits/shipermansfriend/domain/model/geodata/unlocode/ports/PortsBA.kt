package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsBA {
    val PORTS: List<PortCode> = listOf(
        PortCode("BA", "4CA", "Cazin", Location(44.966667, 15.933333)),
        PortCode("BA", "BIR", "Biracevac", null),
        PortCode("BA", "DOJ", "Domaljevac", Location(45.05, 18.566667)),
        PortCode("BA", "FOJ", "Fojnica", Location(43.95, 17.9)),
        PortCode("BA", "JBA", "Jablanica", Location(43.65, 17.75)),
        PortCode("BA", "MAG", "Maglaj", Location(44.533333, 18.083333)),
        PortCode("BA", "NEM", "Neum", Location(42.916667, 17.616667)),
        PortCode("BA", "SMT", "Sanski Most", Location(44.766667, 16.666667)),
        PortCode("BA", "VGS", "Vogosca", Location(43.9, 18.35)),
        PortCode("BA", "ZNK", "Zvornik", Location(44.383333, 19.1))
    )
}
