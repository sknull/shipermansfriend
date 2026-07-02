package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsVC {
    val PORTS: List<PortCode> = listOf(
        PortCode("VC", "BQU", "Port Elizabeth, Bequia", Location(13.0, -61.216667)),
        PortCode("VC", "CAN", "Canouan Island", Location(12.716667, -61.316667)),
        PortCode("VC", "CRP", "Campden Park", Location(13.166667, -61.233333)),
        PortCode("VC", "GRG", "Georgetown", Location(13.266667, -61.116667)),
        PortCode("VC", "KTN", "Kingstown", Location(13.133333, -61.216667)),
        PortCode("VC", "MQS", "Mustique Island", null),
        PortCode("VC", "SVD", "Saint Vincent", null),
        PortCode("VC", "UNI", "Union Island", null)
    )
}
