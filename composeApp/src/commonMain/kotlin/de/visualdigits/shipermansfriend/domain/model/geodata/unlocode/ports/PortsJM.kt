package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsJM {
    val PORTS: List<PortCode> = listOf(
        PortCode("JM", "ALP", "Alligator Pond", null),
        PortCode("JM", "BLR", "Black River", null),
        PortCode("JM", "BWN", "Bowden", null),
        PortCode("JM", "FMH", "Falmouth", null),
        PortCode("JM", "KIN", "Kingston", null),
        PortCode("JM", "MBJ", "Montego Bay", null),
        PortCode("JM", "OCJ", "Ocho Rios", null),
        PortCode("JM", "ORC", "Oracabessa", Location(18.4, -76.95)),
        PortCode("JM", "PEV", "Port Esquivel", null),
        PortCode("JM", "PHE", "Port Henderson", Location(17.95, -76.883333)),
        PortCode("JM", "PKS", "Port Kaiser", null),
        PortCode("JM", "PMO", "Port Morant", null),
        PortCode("JM", "POT", "Port Antonio", null),
        PortCode("JM", "PRH", "Port Rhoades", null),
        PortCode("JM", "PRO", "Port Royal", null),
        PortCode("JM", "RIB", "Rio Bueno", null),
        PortCode("JM", "ROP", "Rocky Point", null),
        PortCode("JM", "SAW", "Saint Ann's Bay", Location(18.433333, -77.2)),
        PortCode("JM", "SLM", "Savanna-la-Mar", Location(18.216667, -78.133333)),
        PortCode("JM", "SRI", "Salt River", null)
    )
}
