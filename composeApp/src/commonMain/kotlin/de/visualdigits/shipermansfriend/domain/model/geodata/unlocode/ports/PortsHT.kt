package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsHT {
    val PORTS: List<PortCode> = listOf(
        PortCode("HT", "ACA", "Les Cayes", null),
        PortCode("HT", "CAP", "Cap-Haïtien", Location(19.75, -72.2)),
        PortCode("HT", "CRC", "Caracol", Location(19.683333, -72.016667)),
        PortCode("HT", "FLI", "Fort Liberte", null),
        PortCode("HT", "FOM", "Fond Mombin", null),
        PortCode("HT", "GVS", "Gonaïves", null),
        PortCode("HT", "JAK", "Jacmel", null),
        PortCode("HT", "JEE", "Jérémie", null),
        PortCode("HT", "LFF", "Lafiteau", Location(18.683333, -72.35)),
        PortCode("HT", "MIR", "Miragoane", null),
        PortCode("HT", "PAP", "Port-au-Prince", null),
        PortCode("HT", "PEG", "Petit Goâve", Location(18.433333, -72.866667)),
        PortCode("HT", "PLH", "Lafito Port", Location(18.683333, -72.35)),
        PortCode("HT", "SMC", "Baie de Saint-Marc", Location(19.116667, -72.7))
    )
}
