package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsYT {
    val PORTS: List<PortCode> = listOf(
        PortCode("YT", "DZA", "Dzaoudzi-Pamandzi Apt", Location(-12.8, 45.283333)),
        PortCode("YT", "KNK", "Kani-Kéli", Location(-12.95, 45.1)),
        PortCode("YT", "LON", "Longoni", Location(-12.716667, 45.166667)),
        PortCode("YT", "MAM", "Mamoudzou", Location(-12.783333, 45.233333))
    )
}
