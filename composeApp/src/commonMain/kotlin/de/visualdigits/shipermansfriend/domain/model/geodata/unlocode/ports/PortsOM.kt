package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsOM {
    val PORTS: List<PortCode> = listOf(
        PortCode("OM", "DQM", "Duqm", Location(19.65, 57.7)),
        PortCode("OM", "FAH", "Fahal", Location(23.683333, 58.5)),
        PortCode("OM", "MCT", "Muscat", Location(23.6, 58.583333)),
        PortCode("OM", "MFH", "Mina' al Fahl", Location(23.633333, 58.516667)),
        PortCode("OM", "MNH", "Al Mudayq", Location(23.816667, 57.516667)),
        PortCode("OM", "MUT", "Muthra", null),
        PortCode("OM", "OFC", "Sur", Location(22.65, 59.433333)),
        PortCode("OM", "OPQ", "Port Qaboos", null),
        PortCode("OM", "QAL", "Qalhat", Location(22.7, 59.366667)),
        PortCode("OM", "QUO", "Quoin Island", Location(26.583333, 56.516667)),
        PortCode("OM", "RAY", "Raysut", null),
        PortCode("OM", "SHI", "Shinas", Location(24.733333, 56.466667)),
        PortCode("OM", "SLL", "Salalah", null),
        PortCode("OM", "SOH", "Sohar", null),
        PortCode("OM", "STQ", "Mina Sultan Qaboos, Muscat", Location(23.616667, 58.55)),
        PortCode("OM", "SUL", "Port Sultan", Location(23.616667, 58.633333)),
        PortCode("OM", "SUW", "Al-Suwaiq", Location(23.85, 57.433333))
    )
}
