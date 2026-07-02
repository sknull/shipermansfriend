package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object PortsUA {
    val PORTS: List<PortCode> = listOf(
        PortCode("UA", "BGD", "Bilhorod-Dnistrovskyi", Location(46.183333, 30.366667)),
        PortCode("UA", "DSK", "Svitlovodsk", Location(49.05, 33.233333)),
        PortCode("UA", "ERD", "Berdiansk", Location(46.75, 36.766667)),
        PortCode("UA", "ILK", "Chornomorsk", Location(46.316667, 30.666667)),
        PortCode("UA", "IZM", "Izmail", Location(45.316667, 28.85)),
        PortCode("UA", "KHE", "Kherson", Location(46.616667, 32.616667)),
        PortCode("UA", "KIA", "Kiliia", Location(45.433333, 29.266667)),
        PortCode("UA", "MPW", "Mariupol", Location(47.05, 37.5)),
        PortCode("UA", "NLV", "Mykolaiv", Location(46.933333, 32.0)),
        PortCode("UA", "OCH", "Ochakiv", Location(46.6, 31.566667)),
        PortCode("UA", "OCT", "Olviia", Location(46.833333, 31.95)),
        PortCode("UA", "ODS", "Odesa", Location(46.5, 30.75)),
        PortCode("UA", "RNI", "Reni", Location(45.416667, 28.283333)),
        PortCode("UA", "SKD", "Skadovsk", Location(46.1, 32.9)),
        PortCode("UA", "UDY", "Ust-Dunaisk/Vylkove", Location(45.466667, 29.7)),
        PortCode("UA", "VYL", "Vylkove", Location(45.4, 29.583333)),
        PortCode("UA", "YUZ", "Pivdennyi", Location(46.6, 31.016667)),
        PortCode("UA", "ZPR", "Zaporizhzhia", Location(47.816667, 35.166667))
    )
}
