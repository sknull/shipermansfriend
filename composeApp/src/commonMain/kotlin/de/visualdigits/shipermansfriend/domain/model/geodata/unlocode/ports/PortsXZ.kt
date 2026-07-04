package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode

object PortsXZ {
    val PORTS: List<PortCode> = listOf(
        PortCode("XZ", "AAD", "Aasgard", Location(65.083333, 6.733333)),
        PortCode("XZ", "ANT", "Ardjuna", Location(-5.9, 105.95)),
        PortCode("XZ", "ANU", "Anoa Natuna", Location(5.233333, 105.6)),
        PortCode("XZ", "BUV", "Buffalo Venture", null),
        PortCode("XZ", "CMT", "Camar Marine Terminal", Location(-6.3, 113.0)),
        PortCode("XZ", "DHS", "Dai Hung (STS Load)", Location(8.866667, 103.3)),
        PortCode("XZ", "DHT", "Dai Hung (Tandem Load)", Location(8.466667, 108.683333)),
        PortCode("XZ", "DRA", "Draugen", Location(64.333333, 7.75)),
        PortCode("XZ", "DTL", "Dulang Marine Terminal", Location(5.8, 104.166667)),
        PortCode("XZ", "HEI", "Heidrun", Location(65.316667, 7.333333)),
        PortCode("XZ", "KMT", "Kakap Marine Terminal", Location(5.016667, 105.95)),
        PortCode("XZ", "LAM", "Laminaria Terminal", Location(-11.0, 126.0)),
        PortCode("XZ", "LGT", "Legendre Terminal", null),
        PortCode("XZ", "NJO", "Njord", Location(64.25, 7.2)),
        PortCode("XZ", "NNE", "Norne", Location(66.0, 7.983333)),
        PortCode("XZ", "NTE", "Northern Endeavour", null),
        PortCode("XZ", "OCA", "Co-operation Zone A (AU,ID)", null),
        PortCode("XZ", "SBY", "Sibuko Bay", Location(3.85, 118.1)),
        PortCode("XZ", "SHA", "Al Shaheen terminal", Location(26.583333, 52.0)),
        PortCode("XZ", "STP", "Stage Platform", null),
        PortCode("XZ", "SUU", "Suursaq", Location(80.816667, -66.633333)),
        PortCode("XZ", "TGR", "Tandjung Gerem", Location(5.966667, 105.983333)),
        PortCode("XZ", "WRT", "Widuri Marine Terminal", Location(-4.666667, 106.65)),
        PortCode("XZ", "YET", "Yetagun Field", Location(13.05, 96.85))
    )
}
