package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsME {
    val PORTS: List<PortCode> = listOf(
        PortCode("ME", "BAR", "Bar", Location(42.083333, 19.083333)),
        PortCode("ME", "BIJ", "Bijela", Location(42.45, 18.666667)),
        PortCode("ME", "BUD", "Budva", Location(42.283333, 18.833333)),
        PortCode("ME", "HNO", "Hercegnovi", Location(42.45, 18.533333)),
        PortCode("ME", "IGL", "Igalo", Location(42.466667, 18.516667)),
        PortCode("ME", "KOT", "Kotor", Location(42.416667, 18.766667)),
        PortCode("ME", "PVC", "Petrovac", Location(42.2, 18.933333)),
        PortCode("ME", "RSN", "Risan (Risano)", Location(42.516667, 18.7)),
        PortCode("ME", "TIV", "Tivat", Location(42.433333, 18.7)),
        PortCode("ME", "ULC", "Ulcinj", Location(41.916667, 19.2)),
        PortCode("ME", "ZEL", "Zelenika", Location(42.45, 18.583333))
    )
}
