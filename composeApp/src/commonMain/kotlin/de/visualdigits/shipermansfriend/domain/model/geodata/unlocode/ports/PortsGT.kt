package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsGT {
    val PORTS: List<PortCode> = listOf(
        PortCode("GT", "CHR", "Champerico", Location(14.3, -91.916667)),
        PortCode("GT", "E8C", "El Carmen", Location(15.15, -90.233333)),
        PortCode("GT", "ELE", "El Estor", null),
        PortCode("GT", "LAA", "La Aurora", null),
        PortCode("GT", "LIV", "Lívingston", Location(15.833333, -88.75)),
        PortCode("GT", "PBR", "Puerto Barrios", null),
        PortCode("GT", "PRQ", "Puerto Quetzal", null),
        PortCode("GT", "RIO", "Río Bravo", Location(14.4, -91.316667)),
        PortCode("GT", "SNJ", "San Jose", null),
        PortCode("GT", "STC", "Puerto Santo Tomás de Castilla", Location(15.7, -88.616667)),
        PortCode("GT", "TUC", "Tecun Uman", null),
        PortCode("GT", "VIN", "Villa Nueva", null)
    )
}
