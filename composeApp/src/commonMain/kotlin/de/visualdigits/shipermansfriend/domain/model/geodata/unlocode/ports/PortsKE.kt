package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsKE {
    val PORTS: List<PortCode> = listOf(
        PortCode("KE", "KIL", "Kilindini", null),
        PortCode("KE", "KIS", "Kisumu", null),
        PortCode("KE", "LAU", "Lamu", null),
        PortCode("KE", "MBA", "Mombasa", null),
        PortCode("KE", "MYD", "Malindi", null),
        PortCode("KE", "SIA", "Siaya", Location(0.066667, 34.283333)),
        PortCode("KE", "SMN", "Shimoni", Location(-4.65, 39.383333))
    )
}
