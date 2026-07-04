package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsSG {
    val PORTS: List<PortCode> = listOf(
        PortCode("SG", "AYC", "Pulau Ayer Chawan", Location(1.266667, 103.7)),
        PortCode("SG", "CHG", "Changi", Location(1.35, 103.983333)),
        PortCode("SG", "JUR", "Jurong", Location(1.333333, 103.7)),
        PortCode("SG", "KEP", "Keppel", Location(1.266667, 103.833333)),
        PortCode("SG", "PAP", "Pasir Panjang", Location(1.266667, 103.783333)),
        PortCode("SG", "PPT", "PASIR PANJANG Terminal", Location(1.283333, 103.766667)),
        PortCode("SG", "PUB", "Pulau Bukom", Location(1.233333, 103.766667)),
        PortCode("SG", "SCT", "Singapore Container Terminal", null),
        PortCode("SG", "SEB", "Pulau Sebarok", Location(1.2, 103.8)),
        PortCode("SG", "SEM", "Sembawang", Location(1.45, 103.816667)),
        PortCode("SG", "SIN", "Singapore", Location(1.283333, 103.85)),
        PortCode("SG", "SLT", "Seletar", Location(1.4, 103.866667)),
        PortCode("SG", "TPG", "Tanjong Pagar", Location(1.266667, 103.85)),
        PortCode("SG", "TPN", "Tanjong Penjuru", Location(1.316667, 103.733333)),
        PortCode("SG", "TUA", "Tuas", Location(1.316667, 103.65))
    )
}
