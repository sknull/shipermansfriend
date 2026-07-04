package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsLV {
    val PORTS: List<PortCode> = listOf(
        PortCode("LV", "EGE", "Engure", Location(57.166667, 23.216667)),
        PortCode("LV", "LPX", "Liepaja", Location(56.516667, 21.016667)),
        PortCode("LV", "MRX", "Mersrags", Location(57.333333, 23.1)),
        PortCode("LV", "PVT", "Pavilosta", Location(56.883333, 21.183333)),
        PortCode("LV", "RIX", "Riga", null),
        PortCode("LV", "ROJ", "Roja", Location(57.5, 22.8)),
        PortCode("LV", "SAL", "Salacgriva", Location(57.75, 24.35)),
        PortCode("LV", "SKU", "Skulte", Location(57.316667, 24.433333)),
        PortCode("LV", "VNT", "Ventspils", null),
        PortCode("LV", "ZJA", "Jurmala", Location(56.95, 23.75))
    )
}
