package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsKW {
    val PORTS: List<PortCode> = listOf(
        PortCode("KW", "DOH", "Doha", Location(29.383333, 47.8)),
        PortCode("KW", "JBD", "Jebel Dhana", null),
        PortCode("KW", "KHT", "As Sulaybikhat", null),
        PortCode("KW", "KWI", "Kuwait", null),
        PortCode("KW", "KWM", "Khor al Mufatta", null),
        PortCode("KW", "MEA", "Mina' al Ahmadi", Location(29.066667, 48.15)),
        PortCode("KW", "MIB", "Mina' 'Abd Allah", Location(29.016667, 48.166667)),
        PortCode("KW", "MIS", "Mina Saud", Location(28.766667, 48.466667)),
        PortCode("KW", "MZR", "Mina Al Zour", Location(28.716667, 48.4)),
        PortCode("KW", "SAA", "Shuaiba", Location(29.033333, 48.133333)),
        PortCode("KW", "SAL", "As Salimiyah", Location(29.333333, 48.083333)),
        PortCode("KW", "SMY", "Salmiya", Location(29.333333, 48.083333)),
        PortCode("KW", "SWK", "Shuwaikh", Location(29.35, 47.933333))
    )
}
