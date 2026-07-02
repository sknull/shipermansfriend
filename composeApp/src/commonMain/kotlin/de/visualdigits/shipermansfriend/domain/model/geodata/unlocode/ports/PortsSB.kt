package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsSB {
    val PORTS: List<PortCode> = listOf(
        PortCode("SB", "AKS", "Auki, Malaita Is", null),
        PortCode("SB", "ALB", "Allardyce Harbour, Sta Isabel Is", null),
        PortCode("SB", "AOB", "Aola Bay", null),
        PortCode("SB", "CHY", "Choiseul Bay", Location(-6.7, 156.433333)),
        PortCode("SB", "GZO", "Gizo", Location(-8.1, 156.85)),
        PortCode("SB", "HIR", "Honiara, Guadalcanal Is", null),
        PortCode("SB", "IRA", "Kirakira, San Cristobal Is", null),
        PortCode("SB", "LAT", "Lata", Location(-10.716667, 165.8)),
        PortCode("SB", "LEV", "Lever Harbour", Location(-8.016667, 157.6)),
        PortCode("SB", "LOF", "Lofung", null),
        PortCode("SB", "NEM", "Nemba", Location(-10.766667, 165.7)),
        PortCode("SB", "NOR", "Noro, New Georgia", null),
        PortCode("SB", "RIN", "Ringgi Cove, Kolombangara", Location(-8.116667, 157.116667)),
        PortCode("SB", "RUS", "Marau Sound, Guadalcanal Is", null),
        PortCode("SB", "SCZ", "Santa Cruz Is", null),
        PortCode("SB", "SHH", "Shortland Harbour", Location(-7.083333, 155.866667)),
        PortCode("SB", "TLG", "Tulagi, Ngella", null),
        PortCode("SB", "VIU", "Viru Harbour", null),
        PortCode("SB", "XYA", "Yandina, Russell Island", Location(-9.116667, 159.216667))
    )
}
