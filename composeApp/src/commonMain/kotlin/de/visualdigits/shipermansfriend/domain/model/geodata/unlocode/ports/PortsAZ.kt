package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsAZ {
    val PORTS: List<PortCode> = listOf(
        PortCode("AZ", "ALA", "Alat", Location(39.95, 49.4)),
        PortCode("AZ", "BAK", "Baku", null),
        PortCode("AZ", "KMZ", "Khachmaz", Location(41.983333, 47.583333)),
        PortCode("AZ", "QDG", "Qaradag", Location(40.25, 49.6)),
        PortCode("AZ", "SHI", "Shirvan", Location(39.916667, 48.916667)),
        PortCode("AZ", "SUQ", "Sumqayit", Location(40.583333, 49.666667))
    )
}
