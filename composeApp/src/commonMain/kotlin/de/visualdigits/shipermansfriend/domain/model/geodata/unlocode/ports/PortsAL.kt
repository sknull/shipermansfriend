package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsAL {
    val PORTS: List<PortCode> = listOf(
        PortCode("AL", "BUT", "Butrint", Location(39.75, 20.016667)),
        PortCode("AL", "DRZ", "Durrës", null),
        PortCode("AL", "HMR", "Himare", Location(40.133333, 19.716667)),
        PortCode("AL", "ROM", "Romano Port", Location(41.366667, 19.416667)),
        PortCode("AL", "SAR", "Sarandë", Location(39.883333, 20.0)),
        PortCode("AL", "SHG", "Shëngjin", Location(41.8, 19.566667)),
        PortCode("AL", "VOA", "Vlorë", Location(40.466667, 19.483333))
    )
}
