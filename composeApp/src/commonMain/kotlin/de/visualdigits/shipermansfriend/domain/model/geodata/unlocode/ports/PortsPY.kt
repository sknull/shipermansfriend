package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsPY {
    val PORTS: List<PortCode> = listOf(
        PortCode("PY", "AGT", "Ciudad del Este", null),
        PortCode("PY", "ASU", "Asunción", Location(-25.266667, -57.666667)),
        PortCode("PY", "BCM", "Barrio Caacupé Mí", Location(-25.216667, -57.55)),
        PortCode("PY", "CAA", "Caacupé", Location(-25.383333, -57.133333)),
        PortCode("PY", "CAC", "Calera Cue", Location(-22.533333, -57.816667)),
        PortCode("PY", "CNP", "Concepción", Location(-23.4, -57.433333)),
        PortCode("PY", "ENO", "Encarnación", Location(-27.333333, -55.9)),
        PortCode("PY", "FNX", "Fenix", null),
        PortCode("PY", "FUO", "Fuerte Olimpo", Location(-21.05, -57.866667)),
        PortCode("PY", "ITE", "Ita Enramada", null),
        PortCode("PY", "MRA", "Colonia Mariano Roque Alonso", Location(-25.166667, -57.55)),
        PortCode("PY", "PAN", "Puerto Antequera", Location(-24.083333, -57.2)),
        PortCode("PY", "PCJ", "Puerto la Victoria", Location(-22.283333, -57.933333)),
        PortCode("PY", "PGU", "Puerto Guarani", Location(-21.3, -57.916667)),
        PortCode("PY", "PSA", "Puerto Sara", Location(-25.433333, -57.533333)),
        PortCode("PY", "SAN", "San Antonio", null),
        PortCode("PY", "TER", "Terport (San Antonio)", Location(-25.383333, -57.633333)),
        PortCode("PY", "VLL", "Villeta", null)
    )
}
