package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsLR {
    val PORTS: List<PortCode> = listOf(
        PortCode("LR", "CMT", "Cape Mount", null),
        PortCode("LR", "CPA", "Cape Palmas", null),
        PortCode("LR", "FIM", "Fimibo", null),
        PortCode("LR", "GBS", "Grand Bassa", null),
        PortCode("LR", "GRE", "Greenville", null),
        PortCode("LR", "HAR", "Harper", Location(4.366667, -7.716667)),
        PortCode("LR", "LOB", "Lower Buchanan", null),
        PortCode("LR", "MAR", "Marshall", null),
        PortCode("LR", "MLW", "Monrovia", Location(6.3, -10.8)),
        PortCode("LR", "ROX", "Robertsport", null),
        PortCode("LR", "RVC", "River Cess", Location(5.466667, -9.583333)),
        PortCode("LR", "SAB", "Sarioe Bay", null),
        PortCode("LR", "SAZ", "Sasstown", null),
        PortCode("LR", "SNI", "Sinoe", null),
        PortCode("LR", "TRT", "Trade Town", null),
        PortCode("LR", "UCN", "Buchanan", null)
    )
}
