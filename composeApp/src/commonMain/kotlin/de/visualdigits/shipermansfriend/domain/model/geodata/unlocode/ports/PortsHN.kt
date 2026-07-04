package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsHN {
    val PORTS: List<PortCode> = listOf(
        PortCode("HN", "AMP", "Amapala", null),
        PortCode("HN", "GJA", "Guanaja", null),
        PortCode("HN", "HNN", "Henecan", null),
        PortCode("HN", "LCE", "La Ceiba", null),
        PortCode("HN", "OMO", "Omoa", Location(15.766667, -88.033333)),
        PortCode("HN", "PCA", "Puerto Castilla", null),
        PortCode("HN", "PCR", "Puerto Cortés", null),
        PortCode("HN", "RTB", "Roatán", Location(16.3, -86.55)),
        PortCode("HN", "RTM", "Mahogany Bay", Location(16.333333, -86.5)),
        PortCode("HN", "SAP", "San Pedro Sula", null),
        PortCode("HN", "SLO", "San Lorenzo", null),
        PortCode("HN", "TEA", "Tela", null),
        PortCode("HN", "TGU", "Tegucigalpa", null),
        PortCode("HN", "TJI", "Trujillo", null)
    )
}
