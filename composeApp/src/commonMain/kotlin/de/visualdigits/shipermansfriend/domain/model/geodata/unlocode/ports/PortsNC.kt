package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsNC {
    val PORTS: List<PortCode> = listOf(
        PortCode("NC", "DUS", "Ducos", Location(-22.233333, 166.45)),
        PortCode("NC", "KOU", "Kouaoua", Location(-21.4, 165.833333)),
        PortCode("NC", "LIF", "Lifou", Location(-20.966667, 167.233333)),
        PortCode("NC", "MEE", "Maré", Location(-21.5, 167.983333)),
        PortCode("NC", "MTD", "Le Mont-Dore", Location(-22.283333, 166.583333)),
        PortCode("NC", "NAK", "Nakéty", Location(-21.55, 166.033333)),
        PortCode("NC", "NEP", "Népoui", Location(-21.316667, 165.0)),
        PortCode("NC", "NOU", "Nouméa", Location(-22.266667, 166.45)),
        PortCode("NC", "PAA", "Paagoumène", Location(-20.483333, 164.2)),
        PortCode("NC", "PNY", "Prony", Location(-22.316667, 166.816667)),
        PortCode("NC", "POR", "Poro", Location(-21.3, 165.716667)),
        PortCode("NC", "THI", "Thio", null),
        PortCode("NC", "TON", "Tontouta Apt", null),
        PortCode("NC", "TUD", "Teoudie", Location(-20.766667, 164.4)),
        PortCode("NC", "VAV", "Vavouto", Location(-21.0, 164.666667)),
        PortCode("NC", "WAL", "Wala", Location(-19.716667, 163.65))
    )
}
