package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsIQ {
    val PORTS: List<PortCode> = listOf(
        PortCode("IQ", "ALF", "Abu Al Fulus", null),
        PortCode("IQ", "ASD", "Al Asad", Location(33.783333, 42.433333)),
        PortCode("IQ", "BGW", "Baghdåd", Location(33.333333, 44.383333)),
        PortCode("IQ", "BSR", "Basra", null),
        PortCode("IQ", "FAO", "Fao", null),
        PortCode("IQ", "HIL", "Al Hillah", Location(32.483333, 44.433333)),
        PortCode("IQ", "IKD", "Iskandariyha", null),
        PortCode("IQ", "KAR", "Karbala", null),
        PortCode("IQ", "KAZ", "Khor al Zubair", null),
        PortCode("IQ", "KHA", "Khor Al Amaya", null),
        PortCode("IQ", "KIK", "Kirkuk", null),
        PortCode("IQ", "MAB", "Basrah Oil Terminal", Location(29.75, 48.833333)),
        PortCode("IQ", "MAN", "Mandali", null),
        PortCode("IQ", "NAS", "Najat", null),
        PortCode("IQ", "NJF", "Najaf", Location(31.983333, 44.3)),
        PortCode("IQ", "NSR", "Nasiriyah", Location(31.05, 46.25)),
        PortCode("IQ", "OSM", "Mosul (Ak Mawsil)", Location(36.166667, 42.583333)),
        PortCode("IQ", "SAM", "Samarra'", Location(34.2, 43.866667)),
        PortCode("IQ", "TJI", "Taji", Location(33.516667, 44.266667)),
        PortCode("IQ", "TQD", "Al Taqaddum", Location(33.333333, 43.583333)),
        PortCode("IQ", "UQR", "Umm Qasr Port", Location(30.033333, 47.933333)),
        PortCode("IQ", "ZAO", "Zakho", Location(37.133333, 42.7))
    )
}
