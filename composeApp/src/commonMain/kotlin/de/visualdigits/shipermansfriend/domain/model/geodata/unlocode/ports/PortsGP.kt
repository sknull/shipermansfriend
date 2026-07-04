package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsGP {
    val PORTS: List<PortCode> = listOf(
        PortCode("GP", "AIF", "Baillif", Location(16.033333, -61.733333)),
        PortCode("GP", "BBR", "Basse-Terre", Location(16.0, -61.716667)),
        PortCode("GP", "BMH", "Baie-Mahault", Location(16.266667, -61.583333)),
        PortCode("GP", "BOU", "Bouillante", Location(16.133333, -61.766667)),
        PortCode("GP", "CBE", "Capesterre-Belle-Eau", Location(16.05, -61.55)),
        PortCode("GP", "DHR", "Deshaies", Location(16.3, -61.783333)),
        PortCode("GP", "FAE", "Folle-Anse", Location(15.95, -61.333333)),
        PortCode("GP", "FPI", "Anse-Bertrand", Location(16.466667, -61.516667)),
        PortCode("GP", "GOS", "Le Gosier", Location(16.2, -61.483333)),
        PortCode("GP", "GRB", "Grand-Bourg", Location(15.883333, -61.3)),
        PortCode("GP", "MSB", "Marigot", Location(18.066667, -63.083333)),
        PortCode("GP", "PBG", "Petit-Bourg", Location(16.183333, -61.583333)),
        PortCode("GP", "PTL", "Port-Louis", Location(16.416667, -61.533333)),
        PortCode("GP", "PTP", "Point-à-Pitre Apt", Location(16.233333, -61.533333)),
        PortCode("GP", "SFC", "Saint-François", Location(16.25, -61.266667))
    )
}
