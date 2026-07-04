package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsBO {
    val PORTS: List<PortCode> = listOf(
        PortCode("BO", "CPB", "Copacabana", Location(-16.15, -69.083333)),
        PortCode("BO", "GUQ", "Guaqui", Location(-16.583333, -68.866667)),
        PortCode("BO", "HTJ", "Huatajata", Location(-16.166667, -68.733333)),
        PortCode("BO", "PBU", "Puerto Busch", Location(-20.033333, -57.966667)),
        PortCode("BO", "PPR", "Puerto Pérez", Location(-16.266667, -68.633333)),
        PortCode("BO", "PSZ", "Puerto Suárez", Location(-18.95, -57.8)),
        PortCode("BO", "PTC", "Puerto Carabuco", Location(-15.75, -69.066667)),
        PortCode("BO", "PUR", "Puerto Rico", Location(-11.083333, -67.633333)),
        PortCode("BO", "QJR", "Puerto Quijarro", Location(-17.783333, -57.766667)),
        PortCode("BO", "RBQ", "Rurrenabaque", Location(-14.466667, -67.566667)),
        PortCode("BO", "RIB", "Riberalta", Location(-10.983333, -66.1)),
        PortCode("BO", "TDD", "Trinidad", Location(-14.833333, -64.9))
    )
}
