package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsPR {
    val PORTS: List<PortCode> = listOf(
        PortCode("PR", "ARE", "Arecibo", null),
        PortCode("PR", "ARR", "Arroyo", Location(17.966667, -66.066667)),
        PortCode("PR", "BQN", "Aguadilla", null),
        PortCode("PR", "GUX", "Guanica", Location(17.966667, -66.916667)),
        PortCode("PR", "GUY", "Guayanilla", Location(17.966667, -66.816667)),
        PortCode("PR", "LAM", "Las Mareas (Guayama)", null),
        PortCode("PR", "MAZ", "Mayagüez", null),
        PortCode("PR", "PJO", "Puerto de Jobos", Location(17.95, -66.183333)),
        PortCode("PR", "PSE", "Ponce", null),
        PortCode("PR", "PYA", "Puerto Yabucoa", Location(18.05, -65.833333)),
        PortCode("PR", "SAL", "Salinas", Location(17.966667, -66.25)),
        PortCode("PR", "SBS", "San Sebastián", Location(18.3, -66.966667)),
        PortCode("PR", "SJU", "San Juan", Location(18.45, -66.083333))
    )
}
