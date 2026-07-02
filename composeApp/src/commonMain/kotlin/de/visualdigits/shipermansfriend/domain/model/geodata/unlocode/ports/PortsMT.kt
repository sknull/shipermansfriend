package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsMT {
    val PORTS: List<PortCode> = listOf(
        PortCode("MT", "BAL", "Balzan", Location(35.9, 14.45)),
        PortCode("MT", "BLA", "Blata l'Bajda", null),
        PortCode("MT", "BZE", "Birzebbugia", Location(35.833333, 14.533333)),
        PortCode("MT", "CKW", "Cirkewwa", null),
        PortCode("MT", "DIS", "Malta Freeport Distripark", Location(35.833333, 14.533333)),
        PortCode("MT", "DMP", "Delimara", Location(35.816667, 14.55)),
        PortCode("MT", "FLO", "Floriana", Location(35.883333, 14.5)),
        PortCode("MT", "GHA", "Ghargur", null),
        PortCode("MT", "GZI", "Gzira", Location(35.9, 14.483333)),
        PortCode("MT", "KIR", "Kirkop", Location(35.833333, 14.483333)),
        PortCode("MT", "MAR", "Marsaxlokk", Location(35.833333, 14.533333)),
        PortCode("MT", "MGZ", "Mgarr, Gozo", Location(36.016667, 14.283333)),
        PortCode("MT", "MLA", "Valletta", Location(35.883333, 14.5)),
        PortCode("MT", "MSA", "Marsa", Location(35.866667, 14.483333)),
        PortCode("MT", "MSS", "Marsaskala", Location(35.866667, 14.55)),
        PortCode("MT", "MSX", "Marsamxett", Location(35.9, 14.5)),
        PortCode("MT", "SLM", "Sliema", Location(35.9, 14.5)),
        PortCode("MT", "SPB", "Saint Paul's Bay (San Pawl il-Bahar)", Location(35.933333, 14.383333)),
        PortCode("MT", "TAR", "Tarxien", Location(35.85, 14.5))
    )
}
