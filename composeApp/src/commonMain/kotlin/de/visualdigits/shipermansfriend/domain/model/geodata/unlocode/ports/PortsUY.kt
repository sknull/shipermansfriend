package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsUY {
    val PORTS: List<PortCode> = listOf(
        PortCode("UY", "CAR", "Carmelo", null),
        PortCode("UY", "CPP", "Cuenca del Plata", Location(-14.616667, -44.116667)),
        PortCode("UY", "CYR", "Colonia", null),
        PortCode("UY", "DZO", "Durazno", null),
        PortCode("UY", "FZB", "Fray Bentos", null),
        PortCode("UY", "JIT", "Jose Ignacio Terminal", null),
        PortCode("UY", "JLC", "Juan L. Lacaze", Location(-34.433333, -57.45)),
        PortCode("UY", "LAP", "La Paloma/Rocha", null),
        PortCode("UY", "MER", "Mercedes", null),
        PortCode("UY", "MVD", "Montevideo", null),
        PortCode("UY", "NVP", "Nueva Palmira", null),
        PortCode("UY", "PDP", "Punta del Este", null),
        PortCode("UY", "PDU", "Paysandu", null),
        PortCode("UY", "PIR", "Piriápolis", Location(-34.866667, -55.266667)),
        PortCode("UY", "STY", "Salto", null)
    )
}
