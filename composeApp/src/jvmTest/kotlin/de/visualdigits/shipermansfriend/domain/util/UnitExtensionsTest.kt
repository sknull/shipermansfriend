package de.visualdigits.shipermansfriend.domain.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UnitExtensionsTest {

    @Test
    fun testParseDistance() {
        assertEquals(10500.0, "10.5 Km".parseDistance())
        assertEquals(10000.0, "10 Km".parseDistance())
        assertEquals(10500.0, "10.5 km".parseDistance())
        assertEquals(10000.0, "10 km".parseDistance())
        assertEquals(10.0, "10 M".parseDistance())
        assertEquals(10.5, "10.5 M".parseDistance())
        assertEquals(10.0, "10 m".parseDistance())
        assertEquals(10.5, "10.5 m".parseDistance())
        assertEquals(10.0, "10".parseDistance())
        assertEquals(10.5, "10.5".parseDistance())
        
        assertEquals(10500.0,"10.5Km".parseDistance())
        assertEquals(10000.0,"10Km".parseDistance())
        assertEquals(10500.0,"10.5km".parseDistance())
        assertEquals(10000.0,"10km".parseDistance())
        assertEquals(10.0,"10M".parseDistance())
        assertEquals(10.5,"10.5M".parseDistance())
        assertEquals(10.0,"10m".parseDistance())
        assertEquals(10.5,"10.5m".parseDistance())
        assertEquals(10.0,"10".parseDistance())
        assertEquals(10.5,"10.5".parseDistance())
    }
}
