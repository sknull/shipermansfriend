package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsCY {
    val PORTS: List<PortCode> = listOf(
        PortCode("CY", "AKT", "Akrotiri", Location(34.6, 32.95)),
        PortCode("CY", "ANM", "Ayia Napa", Location(34.983333, 34.0)),
        PortCode("CY", "DHK", "Dhekelia", Location(34.983333, 33.75)),
        PortCode("CY", "FMG", "Famagusta", Location(35.1, 33.95)),
        PortCode("CY", "KAR", "Karavostasi", Location(35.133333, 32.833333)),
        PortCode("CY", "KYR", "Kyrenia", Location(35.333333, 33.333333)),
        PortCode("CY", "LAT", "Latchi", Location(35.033333, 32.4)),
        PortCode("CY", "LCA", "Larnaca", Location(34.916667, 33.616667)),
        PortCode("CY", "LMS", "Limassol", Location(34.683333, 33.05)),
        PortCode("CY", "MOI", "Moni", Location(34.733333, 33.2)),
        PortCode("CY", "NIC", "Nicosia", Location(35.166667, 33.366667)),
        PortCode("CY", "PFO", "Paphos", Location(34.766667, 32.416667)),
        PortCode("CY", "VAS", "Vasilikos", Location(34.716667, 33.316667)),
        PortCode("CY", "ZYY", "Zyyi", Location(34.733333, 33.333333))
    )
}
