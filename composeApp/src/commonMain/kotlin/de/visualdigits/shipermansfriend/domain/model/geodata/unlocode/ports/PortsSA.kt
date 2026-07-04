package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsSA {
    val PORTS: List<PortCode> = listOf(
        PortCode("SA", "AHA", "Al Hada", null),
        PortCode("SA", "ALK", "Al Khobar", null),
        PortCode("SA", "AMU", "Al Muajjiz", Location(23.466667, 38.383333)),
        PortCode("SA", "AQK", "Al Khobar", Location(26.283333, 50.2)),
        PortCode("SA", "BUR", "Buraydah", Location(26.316667, 43.966667)),
        PortCode("SA", "DHA", "Dhahran", null),
        PortCode("SA", "DHU", "Port of NEOM", Location(27.566667, 34.55)),
        PortCode("SA", "DMM", "Ad Dammam", Location(26.5, 50.2)),
        PortCode("SA", "EJH", "Wedjh", null),
        PortCode("SA", "GIZ", "Jizan", Location(16.9, 42.5)),
        PortCode("SA", "JBI", "Al Jubayl Industrial City", Location(27.083333, 49.7)),
        PortCode("SA", "JEC", "Jazan Economic City", Location(17.283333, 42.333333)),
        PortCode("SA", "JED", "Jeddah", Location(21.466667, 39.166667)),
        PortCode("SA", "JUB", "Jubail", Location(27.016667, 49.666667)),
        PortCode("SA", "JUT", "Juaymah Terminal", Location(26.916667, 50.016667)),
        PortCode("SA", "JYC", "Jeddah Yachts Club Port", Location(21.65, 39.1)),
        PortCode("SA", "KAC", "King Abdullah City", Location(22.4, 39.083333)),
        PortCode("SA", "KHU", "Al Khuraibah", Location(28.05, 35.166667)),
        PortCode("SA", "LIT", "Lith", null),
        PortCode("SA", "MAK", "Makkah", null),
        PortCode("SA", "MAN", "Manailih", null),
        PortCode("SA", "MUF", "Manfouha", null),
        PortCode("SA", "NEO", "Neom", Location(27.566667, 35.55)),
        PortCode("SA", "QAH", "Al Qahmah", Location(18.016667, 41.666667)),
        PortCode("SA", "QAL", "Qalsn", null),
        PortCode("SA", "QUN", "Al Qunfudah", Location(19.116667, 41.066667)),
        PortCode("SA", "QUR", "Qurayyah", null),
        PortCode("SA", "RAB", "Rabigh", null),
        PortCode("SA", "RAM", "Ras al Mishab", null),
        PortCode("SA", "RAR", "Ras al Khafji", null),
        PortCode("SA", "RAZ", "Ras Al-Khair", Location(27.55, 49.2)),
        PortCode("SA", "RTA", "Ras Tanura", Location(26.633333, 50.15)),
        PortCode("SA", "SHU", "Shuaibah", Location(20.683333, 39.516667)),
        PortCode("SA", "SUH", "Salboukh", null),
        PortCode("SA", "VLA", "Umm Lajj", Location(25.016667, 37.266667)),
        PortCode("SA", "YBI", "Yanbu Industrial City", Location(23.933333, 38.25)),
        PortCode("SA", "YNB", "Yanbu commercial city", Location(24.066667, 38.05)),
        PortCode("SA", "ZUY", "Zulayfayn", null)
    )
}
