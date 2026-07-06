package de.visualdigits.shipermansfriend.domain.util

import co.touchlab.kermit.Severity
import org.junit.jupiter.api.Test

class ScratchTest {

    @Test
    fun testSort() {
        val list = listOf(
            Severity.Info,
            Severity.Error,
            Severity.Debug,
            Severity.Verbose,
            Severity.Warn,
        )

        val sorted = list.sortedDescending()

        println(sorted)
    }
}
