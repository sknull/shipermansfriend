package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsKI {
    val PORTS: List<PortCode> = listOf(
        PortCode("KI", "BIR", "Birnie Island", Location(-3.583333, -171.516667)),
        PortCode("KI", "EBI", "Enderbury Island", Location(-3.15, -171.083333)),
        PortCode("KI", "FIS", "Fanning Islands", null),
        PortCode("KI", "HUL", "Hull Island", Location(-4.516667, -172.183333)),
        PortCode("KI", "MCK", "Mckean Island", Location(-3.583333, -174.116667)),
        PortCode("KI", "NIK", "Nikumaroro", Location(-4.683333, -174.516667)),
        PortCode("KI", "PHO", "Phoenix Islands", null),
        PortCode("KI", "SYI", "Sydney Island", Location(-4.466667, -171.25)),
        PortCode("KI", "TRW", "Tarawa", null),
        PortCode("KI", "WNI", "Washington Islands", null)
    )
}
