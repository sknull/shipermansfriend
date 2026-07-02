package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsDO {
    val PORTS: List<PortCode> = listOf(
        PortCode("DO", "AZU", "Azua", Location(18.45, -70.733333)),
        PortCode("DO", "BAN", "Baní", Location(18.283333, -70.333333)),
        PortCode("DO", "BAV", "Bavaro", Location(-18.716667, -68.45)),
        PortCode("DO", "BCC", "Boca Chica", null),
        PortCode("DO", "BQL", "Barcequillo", Location(18.433333, -70.033333)),
        PortCode("DO", "BRX", "Barahona", null),
        PortCode("DO", "CAI", "Catalina Island", Location(18.366667, -69.0)),
        PortCode("DO", "CAL", "Cayo Levantado", Location(19.2, -69.316667)),
        PortCode("DO", "CAU", "Caucedo", Location(18.416667, -69.633333)),
        PortCode("DO", "CBJ", "Cabo Rojo", null),
        PortCode("DO", "CDC", "Casa de Campo", Location(18.4, -68.9)),
        PortCode("DO", "HAI", "Rio Haina", null),
        PortCode("DO", "HIG", "Higüey", Location(18.45, -70.266667)),
        PortCode("DO", "INA", "Bajos de Haina", Location(18.416667, -70.033333)),
        PortCode("DO", "LRM", "La Romana", null),
        PortCode("DO", "MAN", "Manzanillo", Location(19.7, -71.75)),
        PortCode("DO", "OCO", "Ocoa Bay", Location(18.616667, -71.083333)),
        PortCode("DO", "PAL", "Puerto Palenque", Location(18.233333, -70.15)),
        PortCode("DO", "PDR", "Pedernales", null),
        PortCode("DO", "POP", "Puerto Plata", null),
        PortCode("DO", "PUO", "Puerto Libertador", null),
        PortCode("DO", "PVA", "Puerto Viejo de Azua", Location(18.333333, -70.833333)),
        PortCode("DO", "SDQ", "Santo Domingo", Location(18.466667, -69.9)),
        PortCode("DO", "SFN", "San Francisco de Macorís", Location(19.3, -70.25)),
        PortCode("DO", "SNX", "Sabana de la Mar", Location(19.066667, -69.383333)),
        PortCode("DO", "SNZ", "Sánchez", Location(19.233333, -69.616667)),
        PortCode("DO", "SPM", "San Pedro de Macorís", Location(18.45, -69.3)),
        PortCode("DO", "STI", "Santiago de los Caballeros", null),
        PortCode("DO", "VAL", "Villa Altagracia", Location(18.666667, -70.166667))
    )
}
