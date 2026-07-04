package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsFM {
    val PORTS: List<PortCode> = listOf(
        PortCode("FM", "EAU", "Eauripik Atoll", Location(6.7, 143.066667)),
        PortCode("FM", "FSI", "Faisi", null),
        PortCode("FM", "GAF", "Gaferut Atoll", Location(9.233333, 145.383333)),
        PortCode("FM", "IFA", "Ifalik Atoll", Location(7.233333, 144.45)),
        PortCode("FM", "KSA", "Kosrae (ex Kusaie)", null),
        PortCode("FM", "LUK", "Lukunor Atoll", Location(5.516667, 153.766667)),
        PortCode("FM", "NGA", "Ngatik", Location(5.8, 157.266667)),
        PortCode("FM", "PLW", "Puluwat", Location(7.35, 149.183333)),
        PortCode("FM", "PNI", "Pohnpei (ex Ponape)", null),
        PortCode("FM", "PUL", "Pulap Island", Location(7.583333, 149.416667)),
        PortCode("FM", "SAT", "Satawal", Location(7.35, 147.033333)),
        PortCode("FM", "SOR", "Sorol Atoll", Location(8.133333, 140.383333)),
        PortCode("FM", "TKK", "Chuuk (ex Truk)", null),
        PortCode("FM", "WOL", "Woleai Atoll", Location(7.35, 143.866667)),
        PortCode("FM", "YAP", "Yap", null)
    )
}
