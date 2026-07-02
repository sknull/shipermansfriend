package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsGN {
    val PORTS: List<PortCode> = listOf(
        PortCode("GN", "BEL", "Bel-Air", Location(10.233333, -14.466667)),
        PortCode("GN", "BFA", "Boffa", Location(10.183333, -14.033333)),
        PortCode("GN", "BRP", "Port de Boké", Location(10.516667, -14.716667)),
        PortCode("GN", "BTY", "Benty", null),
        PortCode("GN", "CKY", "Conakry", null),
        PortCode("GN", "DKA", "Dubreka", Location(9.783333, -13.5)),
        PortCode("GN", "DPL", "Dapilon", Location(10.85, -14.5)),
        PortCode("GN", "KMC", "Kamsar", Location(10.666667, -14.583333)),
        PortCode("GN", "KMR", "Port-Kamsar", Location(10.65, -14.616667)),
        PortCode("GN", "KTG", "Katougouma", Location(0.05, -14.45)),
        PortCode("GN", "PMT", "Dougoula", Location(10.75, -14.55)),
        PortCode("GN", "SBY", "Moresby", null)
    )
}
