package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsBM {
    val PORTS: List<PortCode> = listOf(
        PortCode("BM", "BDA", "Hamilton", null),
        PortCode("BM", "FPT", "Freeport", null),
        PortCode("BM", "HBI", "Harbour Island", Location(25.5, -76.633333)),
        PortCode("BM", "KWF", "Kings Wharf", null),
        PortCode("BM", "SAN", "Sandys", Location(32.3, -64.866667)),
        PortCode("BM", "SEV", "Devonshire", Location(32.3, -64.75)),
        PortCode("BM", "SGE", "Saint George", Location(32.383333, -64.683333)),
        PortCode("BM", "SOU", "Southampton", Location(32.25, -64.85))
    )
}
