package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsLY {
    val PORTS: List<PortCode> = listOf(
        PortCode("LY", "ABA", "Al Bayda", null),
        PortCode("LY", "ABK", "Abu Kammash", null),
        PortCode("LY", "APO", "Apollonia", null),
        PortCode("LY", "BAR", "Bardiyah", Location(31.766667, 25.1)),
        PortCode("LY", "BEN", "Bingazi (Benghazi)", null),
        PortCode("LY", "BOU", "El Bouri", null),
        PortCode("LY", "BUA", "Bu'ayrat al Hasun", Location(31.4, 15.733333)),
        PortCode("LY", "DRX", "Darnah", Location(32.766667, 22.633333)),
        PortCode("LY", "ELK", "El Choms", Location(32.65, 14.266667)),
        PortCode("LY", "ESI", "As Sidr", Location(30.633333, 18.366667)),
        PortCode("LY", "KHO", "Al Khums", Location(32.65, 14.266667)),
        PortCode("LY", "LMQ", "Marsa Brega", null),
        PortCode("LY", "MEH", "Marsa El Hania", null),
        PortCode("LY", "MEL", "Mellitah (Qasr Ahmed)", Location(32.6, 15.35)),
        PortCode("LY", "MHR", "Marsa el Hariga", Location(32.05, 24.0)),
        PortCode("LY", "MRA", "Misurata", null),
        PortCode("LY", "MTH", "Melittah", null),
        PortCode("LY", "RLA", "Ras Lanuf", null),
        PortCode("LY", "SOU", "Soussah", null),
        PortCode("LY", "SRT", "Sirte (Surt)", null),
        PortCode("LY", "TAG", "Tagiura", null),
        PortCode("LY", "TIP", "Tripoli", null),
        PortCode("LY", "TOA", "Toulmeitha", null),
        PortCode("LY", "TOB", "Tobruk", null),
        PortCode("LY", "TUK", "Tukrah", null),
        PortCode("LY", "ZAW", "Az Zawiyah", Location(32.75, 12.716667)),
        PortCode("LY", "ZLI", "Zliten", null),
        PortCode("LY", "ZUA", "Zuara", null)
    )
}
