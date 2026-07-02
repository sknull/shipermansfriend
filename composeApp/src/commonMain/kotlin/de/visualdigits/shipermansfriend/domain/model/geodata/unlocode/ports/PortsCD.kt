package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsCD {
    val PORTS: List<PortCode> = listOf(
        PortCode("CD", "BNW", "Banana", Location(6.016667, 12.4)),
        PortCode("CD", "BOA", "Boma", null),
        PortCode("CD", "FIH", "Kinshasa", null),
        PortCode("CD", "MAT", "Matadi", null),
        PortCode("CD", "MHB", "Moho Bilondo", Location(-5.916667, 10.25)),
        PortCode("CD", "MKL", "Makala", Location(2.816667, 24.716667))
    )
}
