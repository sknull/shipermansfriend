package de.visualdigits.shipermansfriend.domain.model.geodata.common

import de.visualdigits.common.domain.model.geodata.Location
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LocationTest {

    @Test
    fun testLocation() {
        val locationWuppertal = Location(51.241861, 7.081175)
        val locationHamburg = Location(53.545977, 9.9680454)
        val boundingBox = locationHamburg.calculateBoundingBox(1000.0)

        assertTrue(locationHamburg.isInBoundingBox(boundingBox))
        assertFalse(locationWuppertal.isInBoundingBox(boundingBox))
    }
}
