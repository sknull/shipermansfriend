package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsGF {
    val PORTS: List<PortCode> = listOf(
        PortCode("GF", "CAY", "Cayenne", Location(4.933333, -52.333333)),
        PortCode("GF", "CCY", "Awala-Yalimapo", Location(5.683333, -53.933333)),
        PortCode("GF", "DDC", "Dégrad des Cannes", Location(4.866667, -52.266667)),
        PortCode("GF", "LCY", "Ouanary", Location(4.216667, -51.666667)),
        PortCode("GF", "OYP", "Saint-Georges", Location(3.9, -51.8)),
        PortCode("GF", "QKR", "Kourou", Location(5.15, -52.633333)),
        PortCode("GF", "REM", "Dégrad des Cannes-Pariacabo Pt/Remire", Location(4.883333, -52.283333)),
        PortCode("GF", "SLM", "Saint-Laurent-du-Maroni", Location(5.5, -54.033333))
    )
}
