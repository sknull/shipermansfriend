package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsSV {
    val PORTS: List<PortCode> = listOf(
        PortCode("SV", "APO", "Apopa", Location(13.8, -89.166667)),
        PortCode("SV", "AQJ", "Acajutla", null),
        PortCode("SV", "BEL", "Belfast", null),
        PortCode("SV", "LLD", "La Libertad", Location(13.483333, -89.316667)),
        PortCode("SV", "LUN", "La Union", null),
        PortCode("SV", "MAN", "El Manzanillo", null),
        PortCode("SV", "MIR", "Miramar", null),
        PortCode("SV", "NCU", "Nuevo Cuscatlan", Location(13.633333, -89.25)),
        PortCode("SV", "NHZ", "Nahuizalco", null),
        PortCode("SV", "SAL", "San Salvador", null),
        PortCode("SV", "SSO", "Armenia", Location(13.733333, 89.483333)),
        PortCode("SV", "TUE", "Moncagua", null)
    )
}
