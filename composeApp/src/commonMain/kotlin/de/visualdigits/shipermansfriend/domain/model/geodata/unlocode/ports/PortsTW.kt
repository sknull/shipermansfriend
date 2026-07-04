package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsTW {
    val PORTS: List<PortCode> = listOf(
        PortCode("TW", "APG", "An Ping", null),
        PortCode("TW", "BAL", "Bali", Location(25.15, 121.4)),
        PortCode("TW", "BDA", "Budai", Location(23.366667, 120.1)),
        PortCode("TW", "HOP", "Hoping", null),
        PortCode("TW", "HTC", "Hsin-t'ien", null),
        PortCode("TW", "HUK", "Hukou", Location(24.9, 121.05)),
        PortCode("TW", "HUN", "Hualien", null),
        PortCode("TW", "KEL", "Keelung (Chilung)", null),
        PortCode("TW", "KHH", "Kaohsiung", null),
        PortCode("TW", "LGM", "Lung-men", Location(23.566667, 119.666667)),
        PortCode("TW", "MAL", "Mai-Liai", Location(23.75, 120.25)),
        PortCode("TW", "MLI", "Mai-liao", Location(23.1, 120.133333)),
        PortCode("TW", "NAN", "Nantou", Location(23.9, 120.683333)),
        PortCode("TW", "SHL", "Sha-lun", Location(25.1, 121.216667)),
        PortCode("TW", "SUO", "Suao", null),
        PortCode("TW", "TNN", "Tainan", null),
        PortCode("TW", "TPE", "Taipei", Location(25.033333, 121.516667)),
        PortCode("TW", "TTT", "Taitung", null),
        PortCode("TW", "TXG", "Taichung", null),
        PortCode("TW", "TYN", "Taoyuan", null),
        PortCode("TW", "WTU", "Wu-tu", Location(25.083333, 121.666667)),
        PortCode("TW", "YGE", "Ying-Ge", null),
        PortCode("TW", "YLN", "Yunlin", null)
    )
}
