package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsSY {
    val PORTS: List<PortCode> = listOf(
        PortCode("SY", "ALD", "Al Ladhiqiyah", Location(35.516667, 35.783333)),
        PortCode("SY", "ARW", "Arwad", Location(34.85, 35.85)),
        PortCode("SY", "BAN", "Baniyas", Location(35.183333, 35.95)),
        PortCode("SY", "BEN", "Banghazi", Location(32.116667, 20.066667)),
        PortCode("SY", "DAM", "Damascus (Damas)", null),
        PortCode("SY", "LTK", "Latakia", Location(35.516667, 35.783333)),
        PortCode("SY", "TAO", "Tartus Oil Terminal", Location(34.966667, 35.883333)),
        PortCode("SY", "TTS", "Tartus", Location(34.9, 35.9))
    )
}
