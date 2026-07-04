package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsMQ {
    val PORTS: List<PortCode> = listOf(
        PortCode("MQ", "ADA", "Les Anses-d'Arlet", Location(14.483333, -61.083333)),
        PortCode("MQ", "FDF", "Fort-de-France", Location(14.6, -61.05)),
        PortCode("MQ", "KF4", "Le Vauclin", Location(14.55, -60.85)),
        PortCode("MQ", "LER", "Le Robert", Location(14.666667, -60.933333)),
        PortCode("MQ", "PFI", "Le Marigot", Location(14.816667, -61.033333)),
        PortCode("MQ", "QPC", "Port de Fort-de-France Pt.", Location(14.6, -61.05)),
        PortCode("MQ", "SF4", "Le Precheur", Location(14.8, -61.233333)),
        PortCode("MQ", "WF3", "Case-Pilote", Location(14.633333, -61.133333))
    )
}
