package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsRS {
    val PORTS: List<PortCode> = listOf(
        PortCode("RS", "4SN", "Bresnica", Location(43.866667, 20.583333)),
        PortCode("RS", "APT", "Apatin", Location(45.666667, 18.983333)),
        PortCode("RS", "BPA", "Backa Palanka", Location(45.25, 19.383333)),
        PortCode("RS", "BZD", "Bezdam", Location(45.85, 18.916667)),
        PortCode("RS", "DIM", "Dimitrovgrad", Location(43.0, 22.766667)),
        PortCode("RS", "DOB", "Dobanovci", Location(44.816667, 20.216667)),
        PortCode("RS", "GNJ", "Gnjilane", Location(42.466667, 21.45)),
        PortCode("RS", "KMA", "Kursumlija", Location(43.133333, 21.266667)),
        PortCode("RS", "MSD", "Mesarci", Location(44.6, 19.916667)),
        PortCode("RS", "PHO", "Prahovo", Location(44.283333, 22.583333)),
        PortCode("RS", "SKA", "Sremski Karlovci", Location(45.2, 19.933333)),
        PortCode("RS", "VGS", "Veliko Gradiste", Location(44.75, 21.5)),
        PortCode("RS", "VRA", "Vranje", Location(42.55, 21.9)),
        PortCode("RS", "VS4", "Veternik", Location(45.25, 19.75)),
        PortCode("RS", "XXX", "RIS Inland waterways", null)
    )
}
