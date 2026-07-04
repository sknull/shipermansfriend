package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsHK {
    val PORTS: List<PortCode> = listOf(
        PortCode("HK", "9YM", "Yau Ma Tei", Location(22.316667, 114.166667)),
        PortCode("HK", "ABD", "Aberdeen", Location(22.25, 114.15)),
        PortCode("HK", "ALC", "Ap Lei Chau", null),
        PortCode("HK", "CWB", "Causeway Bay", Location(22.283333, 114.183333)),
        PortCode("HK", "HKC", "Ha Kwai Chung", Location(22.366667, 114.116667)),
        PortCode("HK", "HKG", "Hong Kong", Location(22.316667, 114.166667)),
        PortCode("HK", "KWN", "Kowloon", Location(22.3, 114.166667)),
        PortCode("HK", "LAM", "Lamma Island", null),
        PortCode("HK", "TOL", "Tolo Harbour", null),
        PortCode("HK", "TST", "Tsim Sha Tsui", Location(22.3, 114.166667)),
        PortCode("HK", "VIC", "Victoria", null),
        PortCode("HK", "WNI", "Wan Chai", Location(22.283333, 114.166667)),
        PortCode("HK", "YUE", "Yuen Long", null)
    )
}
