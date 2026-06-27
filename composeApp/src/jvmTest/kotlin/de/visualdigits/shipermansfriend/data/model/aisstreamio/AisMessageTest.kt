package de.visualdigits.shipermansfriend.data.model.aisstreamio

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import java.io.File

class AisMessageTest {

    private val aisJson = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = true
    }

     @Test
     fun testReadModel() {
         val json = File(ClassLoader.getSystemResource("aisstream/position_report.json").toURI()).readText()
         val message = aisJson.decodeFromString<AisMessage>(json)

         println(message)
     }
}
