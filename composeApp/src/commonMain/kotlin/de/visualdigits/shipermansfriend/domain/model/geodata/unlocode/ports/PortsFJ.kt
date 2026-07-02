package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsFJ {
    val PORTS: List<PortCode> = listOf(
        PortCode("FJ", "DEN", "Denarau", Location(-17.766667, 177.383333)),
        PortCode("FJ", "ELL", "Ellington Wharf", Location(-17.35, 178.216667)),
        PortCode("FJ", "LBS", "Labasa", Location(-16.416667, 179.383333)),
        PortCode("FJ", "LTK", "Lautoka", Location(-17.616667, 177.45)),
        PortCode("FJ", "MAL", "Malau (Labasa)", Location(-16.366667, 179.383333)),
        PortCode("FJ", "MOM", "Momi", Location(-17.916667, 177.283333)),
        PortCode("FJ", "NAM", "Nambouwalu", Location(-16.983333, 178.7)),
        PortCode("FJ", "RTA", "Rotuma", null),
        PortCode("FJ", "SIN", "Singatoka", Location(-18.166667, 177.516667)),
        PortCode("FJ", "SUV", "Suva", Location(-18.133333, 178.45)),
        PortCode("FJ", "SVU", "Savusavu", null),
        PortCode("FJ", "VAT", "Vatia Wharf", Location(-17.4, 177.766667)),
        PortCode("FJ", "VUD", "Vuda", Location(-17.6, 177.5)),
        PortCode("FJ", "WAI", "Wairiki", Location(-16.916667, 178.666667))
    )
}
