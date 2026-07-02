package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsTZ {
    val PORTS: List<PortCode> = listOf(
        PortCode("TZ", "DAR", "Dar es Salaam", Location(-6.8, 39.283333)),
        PortCode("TZ", "IKW", "Ikwiriri", Location(-7.983333, 39.016667)),
        PortCode("TZ", "KAG", "Kagera", Location(-4.65, 30.666667)),
        PortCode("TZ", "KIK", "Kilwa Kivinje", null),
        PortCode("TZ", "KIM", "Kilwa Masoko", null),
        PortCode("TZ", "LDI", "Lindi", null),
        PortCode("TZ", "MIC", "Michiuja", null),
        PortCode("TZ", "MIK", "Mikindani", null),
        PortCode("TZ", "MKO", "Mkokotoni, Zanzibar", null),
        PortCode("TZ", "MOH", "Mohoro", null),
        PortCode("TZ", "MTS", "Mtsora", null),
        PortCode("TZ", "MWZ", "Mwanza", Location(-2.516667, 32.9)),
        PortCode("TZ", "MYW", "Mtwara", null),
        PortCode("TZ", "PAN", "Pangani", null),
        PortCode("TZ", "PMA", "Pemba", null),
        PortCode("TZ", "RIJ", "Rijiju", null),
        PortCode("TZ", "SAM", "Samanga", null),
        PortCode("TZ", "TGT", "Tanga", null),
        PortCode("TZ", "TKQ", "Kigoma", null),
        PortCode("TZ", "ZNZ", "Zanzibar", null)
    )
}
