package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsTM {
    val PORTS: List<PortCode> = listOf(
        PortCode("TM", "ALT", "Altyn Asyr", Location(37.883333, 58.366667)),
        PortCode("TM", "CHE", "Cheleken", Location(39.4, 53.133333)),
        PortCode("TM", "HAL", "Halach", Location(38.066667, 64.883333)),
        PortCode("TM", "KRA", "Krasnovodsk", Location(40.016667, 52.966667)),
        PortCode("TM", "KRW", "Turkmenbashi", Location(40.016667, 52.966667)),
        PortCode("TM", "TMZ", "Termez", null)
    )
}
