package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsLB {
    val PORTS: List<PortCode> = listOf(
        PortCode("LB", "ACT", "Amchit", Location(34.15, 35.633333)),
        PortCode("LB", "BEY", "Beirut", Location(33.833333, 35.483333)),
        PortCode("LB", "BRU", "Brummana", Location(35.883333, 35.616667)),
        PortCode("LB", "BYL", "Byblos", Location(34.116667, 35.65)),
        PortCode("LB", "CHK", "Chekka", null),
        PortCode("LB", "DRA", "Dora", Location(33.916667, 35.566667)),
        PortCode("LB", "JIE", "Jieh", null),
        PortCode("LB", "KHA", "Khalde", null),
        PortCode("LB", "KYE", "Tripoli", null),
        PortCode("LB", "LN8", "Bekaa", Location(34.0, 36.133333)),
        PortCode("LB", "OUZ", "Ouzai", null),
        PortCode("LB", "SAY", "Saydå", Location(33.55, 35.366667)),
        PortCode("LB", "SEL", "Selaata", null),
        PortCode("LB", "SUR", "Sur (Tyre)", null),
        PortCode("LB", "ZHR", "Zahrani Terminal", null),
        PortCode("LB", "ZUK", "Zouk", Location(33.966667, 35.633333))
    )
}
