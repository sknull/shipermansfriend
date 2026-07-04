package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsFO {
    val PORTS: List<PortCode> = listOf(
        PortCode("FO", "EDI", "Eidi", Location(62.3, -7.083333)),
        PortCode("FO", "FUG", "Fuglafjørdur", null),
        PortCode("FO", "HUS", "Husevig", null),
        PortCode("FO", "HYV", "Hoyvik", Location(62.033333, -6.75)),
        PortCode("FO", "KOL", "Kollafjördur", Location(62.116667, -6.883333)),
        PortCode("FO", "KVI", "Klaksvik", null),
        PortCode("FO", "LOP", "Lopra", null),
        PortCode("FO", "NLS", "Nólsoy", Location(62.016667, -6.666667)),
        PortCode("FO", "NSK", "Norÿskáli", Location(62.216667, -7.0)),
        PortCode("FO", "RVK", "Runavík", Location(62.116667, -6.716667)),
        PortCode("FO", "SJO", "Sjovar havn", Location(62.1, -6.75)),
        PortCode("FO", "SKA", "Skáli", Location(62.15, -6.766667)),
        PortCode("FO", "SMJ", "Solmundefjord", null),
        PortCode("FO", "SRV", "Sorvágur", Location(62.066667, -7.3)),
        PortCode("FO", "STR", "Strendur", Location(62.116667, -6.75)),
        PortCode("FO", "SYN", "Streymnes", Location(62.2, -7.016667)),
        PortCode("FO", "THO", "Thorshavn", Location(62.016667, -6.766667)),
        PortCode("FO", "TOF", "Toftir", Location(62.1, -6.733333)),
        PortCode("FO", "TOR", "Tórshavn", Location(62.166667, -65.0)),
        PortCode("FO", "TVO", "Tvoroyri", Location(61.55, -6.8)),
        PortCode("FO", "VAG", "Vágur", Location(61.466667, -6.816667)),
        PortCode("FO", "VES", "Vestmanhavn", null),
        PortCode("FO", "VID", "Vadair", null)
    )
}
