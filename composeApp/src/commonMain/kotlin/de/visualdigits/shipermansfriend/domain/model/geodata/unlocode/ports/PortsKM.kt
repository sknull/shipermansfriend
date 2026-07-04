package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsKM {
    val PORTS: List<PortCode> = listOf(
        PortCode("KM", "AJN", "Anjouan Apt", Location(-12.25, 44.416667)),
        PortCode("KM", "FOU", "Foumboni, Moheli", null),
        PortCode("KM", "MUT", "Mutsamudu, Anjouan", Location(-12.15, 44.383333)),
        PortCode("KM", "YVA", "Moroni", null)
    )
}
