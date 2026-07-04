package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsLK {
    val PORTS: List<PortCode> = listOf(
        PortCode("LK", "BRW", "Beruwala", null),
        PortCode("LK", "BTC", "Batticoloa", null),
        PortCode("LK", "CMB", "Colombo", Location(6.916667, 79.85)),
        PortCode("LK", "GAL", "Galle", null),
        PortCode("LK", "HBA", "Hambantota", Location(6.116667, 81.116667)),
        PortCode("LK", "JAF", "Jaffna", null),
        PortCode("LK", "JCT", "Jaya Container Terminal", null),
        PortCode("LK", "KAL", "Kalpitiya", null),
        PortCode("LK", "KAY", "Kayts", null),
        PortCode("LK", "KCT", "Koggala", Location(5.983333, 80.333333)),
        PortCode("LK", "KNK", "Kankesanturai", null),
        PortCode("LK", "KON", "Kondavattavan", Location(7.283333, 81.633333)),
        PortCode("LK", "MAN", "Mannar", null),
        PortCode("LK", "MAW", "Mawella", Location(5.983333, 80.733333)),
        PortCode("LK", "MUL", "Mulativu", null),
        PortCode("LK", "OLU", "Oluvil", Location(7.283333, 81.85)),
        PortCode("LK", "PPE", "Point Pedro", null),
        PortCode("LK", "SGT", "South Asia Gateway Terminal", null),
        PortCode("LK", "TAL", "Talaimannar", null),
        PortCode("LK", "TRR", "Trincomalee", null),
        PortCode("LK", "UCT", "Unity Container Terminal", null)
    )
}
