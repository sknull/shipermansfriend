package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsLT {
    val PORTS: List<PortCode> = listOf(
        PortCode("LT", "BOT", "Botingé", Location(56.066667, 21.116667)),
        PortCode("LT", "KLJ", "Klaipeda", null),
        PortCode("LT", "MLM", "Malku ilankos juru uosto PVP/Klaipeda", Location(55.716667, 21.116667)),
        PortCode("LT", "MOM", "Molo juru uosto PVP/Klaipeda", Location(55.716667, 21.1)),
        PortCode("LT", "PLM", "Pilies juru uosto PVP/Klaipeda", Location(55.716667, 21.083333))
    )
}
