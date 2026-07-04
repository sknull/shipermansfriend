package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsSK {
    val PORTS: List<PortCode> = listOf(
        PortCode("SK", "BAB", "Bratislava Port", null),
        PortCode("SK", "BCK", "Beckov", null),
        PortCode("SK", "DF4", "Spisský Stiavnik", Location(49.0, 20.366667)),
        PortCode("SK", "DVO", "Dvorniky", Location(48.6, 20.833333)),
        PortCode("SK", "DXZ", "Valaska", Location(48.816667, 19.583333)),
        PortCode("SK", "FD3", "Krásno", Location(48.6, 18.316667)),
        PortCode("SK", "GLT", "Galanta", Location(48.2, 17.716667)),
        PortCode("SK", "HIL", "Mojmírovce", Location(48.216667, 18.066667)),
        PortCode("SK", "K87", "Sady nad Torysou", Location(48.7, 21.333333)),
        PortCode("SK", "KAO", "Kolárovo", Location(47.9, 17.983333)),
        PortCode("SK", "KNP", "Komarno Port", null),
        PortCode("SK", "KVE", "Kvetoslavov", Location(48.05, 17.35)),
        PortCode("SK", "MAT", "Martovce", Location(47.85, 18.116667)),
        PortCode("SK", "MOD", "Modra", Location(48.333333, 17.316667)),
        PortCode("SK", "PLB", "Praha", Location(48.366667, 19.516667)),
        PortCode("SK", "POP", "Poprad", Location(49.05, 20.3)),
        PortCode("SK", "SLK", "Siladice", Location(48.366667, 17.75)),
        PortCode("SK", "TBE", "Trencianske Bohuslavice", null),
        PortCode("SK", "TEP", "Teplicka nad Vahom", Location(49.233333, 18.8)),
        PortCode("SK", "TLM", "Tlmace", Location(48.283333, 18.533333)),
        PortCode("SK", "TNA", "Trnava", Location(48.366667, 17.583333)),
        PortCode("SK", "TUT", "Turcianske Teplice", Location(48.866667, 18.866667)),
        PortCode("SK", "VZK", "Vel'ké Zlievce", Location(48.2, 19.45)),
        PortCode("SK", "XCV", "Malá Trna", Location(48.45, 21.683333)),
        PortCode("SK", "XXX", "RIS Inland waterways", null),
        PortCode("SK", "ZIA", "Ziar nad Hronom", Location(48.583333, 18.866667))
    )
}
