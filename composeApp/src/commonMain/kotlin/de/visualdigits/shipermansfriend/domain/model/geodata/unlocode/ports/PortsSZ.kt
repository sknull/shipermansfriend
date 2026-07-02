package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsSZ {
    val PORTS: List<PortCode> = listOf(
        PortCode("SZ", "7DS", "Sidvokodvo", Location(-26.616667, 31.45)),
        PortCode("SZ", "MAL", "Malkerns", Location(-26.566667, 31.183333)),
        PortCode("SZ", "NHL", "Nhlangano", Location(-27.116667, 31.2))
    )
}
