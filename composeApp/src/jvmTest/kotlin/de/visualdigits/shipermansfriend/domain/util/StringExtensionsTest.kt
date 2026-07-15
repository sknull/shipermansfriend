package de.visualdigits.shipermansfriend.domain.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StringExtensionsTest {

    @Test
    fun testCapitalieWords() {
        assertEquals("Hello World", "HELLO WORLD".capitalizeWords())
        assertEquals("Hello-World", "HELLO-WORLD".capitalizeWords())
    }
}
