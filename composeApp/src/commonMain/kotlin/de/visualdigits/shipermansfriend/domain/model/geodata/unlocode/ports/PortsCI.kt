package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsCI {
    val PORTS: List<PortCode> = listOf(
        PortCode("CI", "ABJ", "Abidjan", Location(5.333333, -4.016667)),
        PortCode("CI", "ASS", "Assinie-Mafia", Location(5.116667, -3.283333)),
        PortCode("CI", "BAO", "Baobab Terminal", Location(4.95, -4.55)),
        PortCode("CI", "DAB", "Dabou", Location(5.316667, -4.366667)),
        PortCode("CI", "ESP", "Espoir", Location(5.066667, -4.383333)),
        PortCode("CI", "FRE", "Fresco", Location(5.083333, -5.566667)),
        PortCode("CI", "GBA", "Gbabam", Location(5.466667, -5.583333)),
        PortCode("CI", "GLU", "Grand Lahou", Location(5.133333, -5.016667)),
        PortCode("CI", "JAC", "Jacqueville", Location(5.2, -4.416667)),
        PortCode("CI", "KOS", "Kosagi", Location(8.216667, -8.1)),
        PortCode("CI", "PBT", "Port-Bouët", Location(5.25, -3.966667)),
        PortCode("CI", "SPY", "San-Pédro", Location(4.733333, -6.616667)),
        PortCode("CI", "ZSS", "Sassandra", null)
    )
}
