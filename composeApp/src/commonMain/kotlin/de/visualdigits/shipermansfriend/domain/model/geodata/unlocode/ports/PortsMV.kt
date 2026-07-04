package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsMV {
    val PORTS: List<PortCode> = listOf(
        PortCode("MV", "ADU", "Addu", null),
        PortCode("MV", "BAL", "Baa Atoll", Location(5.133333, 72.95)),
        PortCode("MV", "CPP", "Coastline Private Port", Location(4.166667, 73.45)),
        PortCode("MV", "HHE", "Hulhulé Island", Location(4.183333, 73.516667)),
        PortCode("MV", "HID", "Halaveli", Location(4.033333, 72.916667)),
        PortCode("MV", "HMT", "Hulhumale", Location(4.233333, 73.55)),
        PortCode("MV", "HTP", "Hithadhoo Port", Location(-0.633333, 73.1)),
        PortCode("MV", "KDP", "Kulhudhuffushi Port", Location(6.616667, 73.066667)),
        PortCode("MV", "KEL", "Kelai", null),
        PortCode("MV", "KFP", "Koodoo Fisheries", Location(0.733333, 73.433333)),
        PortCode("MV", "KUL", "Kulhudhuffushi", Location(6.616667, 73.05)),
        PortCode("MV", "MCP", "Male' Commercial Harbor", Location(4.166667, 73.5)),
        PortCode("MV", "MGP", "Maldive Gas", Location(4.183333, 73.45)),
        PortCode("MV", "MLE", "Male", Location(4.166667, 73.5)),
        PortCode("MV", "MMP", "Maamigili Port", Location(3.466667, 72.833333)),
        PortCode("MV", "RGI", "Rangali", Location(3.6, 72.7)),
        PortCode("MV", "VHP", "Villa Hakatha", Location(4.183333, 73.433333))
    )
}
