package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsBH {
    val PORTS: List<PortCode> = listOf(
        PortCode("BH", "AHD", "Al Hidd", Location(26.233333, 50.65)),
        PortCode("BH", "GBQ", "Al Muharraq", Location(26.25, 50.6)),
        PortCode("BH", "KBS", "Khalifa Bin Salman Port", Location(26.2, 50.616667)),
        PortCode("BH", "MIN", "Mina Sulman Port", Location(26.2, 50.616667)),
        PortCode("BH", "SIT", "Sitrah", Location(26.133333, 50.616667))
    )
}
