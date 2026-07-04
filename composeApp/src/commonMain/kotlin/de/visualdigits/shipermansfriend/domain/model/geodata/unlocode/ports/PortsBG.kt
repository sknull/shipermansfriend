package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsBG {
    val PORTS: List<PortCode> = listOf(
        PortCode("BG", "4IG", "Ignatievo", Location(43.25, 27.766667)),
        PortCode("BG", "AKH", "Akhtopol", Location(42.1, 27.95)),
        PortCode("BG", "BAL", "Balchik", Location(43.416667, 28.166667)),
        PortCode("BG", "BOJ", "Burgas", null),
        PortCode("BG", "BSL", "Byala Slatina", Location(43.466667, 23.95)),
        PortCode("BG", "GUR", "Gurkovo", Location(42.65, 25.783333)),
        PortCode("BG", "KUK", "Kuklen", Location(42.033333, 24.783333)),
        PortCode("BG", "MAB", "Marchevo", Location(41.6, 23.8)),
        PortCode("BG", "NES", "Nessebar", Location(42.65, 27.733333)),
        PortCode("BG", "NPO", "Nikopol", Location(43.683333, 24.883333)),
        PortCode("BG", "OKH", "Oryakhovo", Location(43.733333, 23.95)),
        PortCode("BG", "POD", "Podayva", null),
        PortCode("BG", "RDU", "Ruse", Location(43.85, 25.966667)),
        PortCode("BG", "S3W", "Straldzha", Location(42.6, 26.683333)),
        PortCode("BG", "SLS", "Silistra", null),
        PortCode("BG", "SOM", "Somovit", null),
        PortCode("BG", "SOZ", "Sozopol", Location(42.416667, 27.7)),
        PortCode("BG", "SVI", "Svistov", null),
        PortCode("BG", "VAR", "Varna", null),
        PortCode("BG", "VAZ", "Varna-Zapad", null),
        PortCode("BG", "VID", "Vidin", null),
        PortCode("BG", "VT4", "Byala Cherkva", Location(43.2, 25.3)),
        PortCode("BG", "XXX", "RIS Inland waterways", null)
    )
}
