package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsIL {
    val PORTS: List<PortCode> = listOf(
        PortCode("IL", "8UH", "Gush Halav", Location(33.166667, 35.433333)),
        PortCode("IL", "ACR", "Acre", null),
        PortCode("IL", "AKK", "Akko", Location(32.916667, 35.066667)),
        PortCode("IL", "AKL", "Ashkelon", null),
        PortCode("IL", "AS2", "Mishor Adumim", Location(31.783333, 35.316667)),
        PortCode("IL", "ASH", "Ashdod", null),
        PortCode("IL", "AST", "Ashdod Southport", Location(31.816667, 34.65)),
        PortCode("IL", "BGV", "Binyamina-Giv'at Ada", Location(32.516667, 34.933333)),
        PortCode("IL", "ETH", "Elat (Eilath)", null),
        PortCode("IL", "HAD", "Hadera", null),
        PortCode("IL", "HBT", "Haifa Bayport", Location(32.8, 34.983333)),
        PortCode("IL", "HFA", "Haifa", Location(32.8, 34.983333)),
        PortCode("IL", "JUL", "Julis", Location(31.683333, 34.65)),
        PortCode("IL", "KFV", "Kfar Vitkin", Location(32.366667, 34.866667)),
        PortCode("IL", "KY7", "Karmei Yosef", Location(31.833333, 34.916667)),
        PortCode("IL", "M8A", "Magshimim", Location(32.033333, 34.9)),
        PortCode("IL", "MSP", "Haifa Israel Shipyards Port", Location(32.8, 34.983333)),
        PortCode("IL", "NAB", "Nabulus", Location(32.216667, 35.25)),
        PortCode("IL", "NAT", "Netanya", Location(32.333333, 34.866667)),
        PortCode("IL", "OFR", "Ofra", Location(31.95, 35.25)),
        PortCode("IL", "TIB", "Tiberias", Location(32.8, 35.533333)),
        PortCode("IL", "TLV", "Tel Aviv-Yafo", Location(32.05, 34.75))
    )
}
