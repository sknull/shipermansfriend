package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsKP {
    val PORTS: List<PortCode> = listOf(
        PortCode("KP", "CHO", "Chongjin", null),
        PortCode("KP", "GEN", "Gensan", null),
        PortCode("KP", "HAE", "Haeju", null),
        PortCode("KP", "HGM", "Hungnam", null),
        PortCode("KP", "NAM", "Nampo", null),
        PortCode("KP", "ODA", "Odaejin", Location(41.383333, 129.75)),
        PortCode("KP", "RIW", "Riwon", Location(40.316667, 128.633333)),
        PortCode("KP", "RJN", "Rajin (Najin)", Location(42.216667, 130.283333)),
        PortCode("KP", "SAM", "Samcha do", Location(39.4, 124.716667)),
        PortCode("KP", "SGN", "Songnim", Location(38.766667, 125.583333)),
        PortCode("KP", "SIN", "Sinpo", Location(40.066667, 128.266667)),
        PortCode("KP", "SON", "Songjin", null),
        PortCode("KP", "TCH", "Tanchon", Location(40.416667, 128.933333)),
        PortCode("KP", "WON", "Wonsan", null)
    )
}
