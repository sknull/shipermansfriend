package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsQA {
    val PORTS: List<PortCode> = listOf(
        PortCode("QA", "ASN", "Al Shaheen", Location(26.55, 52.066667)),
        PortCode("QA", "DOH", "Doha", null),
        PortCode("QA", "HAL", "Halul", null),
        PortCode("QA", "HMD", "BGN/PCGN1956 - HAMAD", Location(25.0, 51.616667)),
        PortCode("QA", "HNA", "Hanna", null),
        PortCode("QA", "QAP", "Qapco", Location(24.916667, 51.566667)),
        PortCode("QA", "QCH", "Qchem", Location(24.85, 51.533333)),
        PortCode("QA", "RLF", "Ras Laffan", Location(25.933333, 51.533333)),
        PortCode("QA", "RUS", "Al Ruwais Qatar", Location(26.233333, 51.333333)),
        PortCode("QA", "SLW", "As Salwa", Location(24.733333, 50.75)),
        PortCode("QA", "UMS", "Umm Sa'id (Mesaieed)", Location(24.983333, 51.533333))
    )
}
