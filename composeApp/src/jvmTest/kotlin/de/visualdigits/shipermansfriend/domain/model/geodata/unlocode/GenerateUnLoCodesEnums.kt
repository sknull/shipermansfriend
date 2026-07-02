package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode

import de.visualdigits.common.domain.model.geodata.toLocation
import org.junit.jupiter.api.Test
import java.io.File
import java.net.URI
import kotlin.math.roundToInt
import kotlin.test.assertEquals

class GenerateUnLoCodesEnums {

    @Test
    fun generatePortCode() {
        generateCountryEnum()
        generatePortDataFiles()
    }

    @Test
    fun testFindPort() {
        val port1 = PortRegistry.findPort("deHam ")
        assertEquals("HAM", port1!!.code)

        val port2 = PortRegistry.findPort("BCD")
        assertEquals("Bacolod, Negros", port2!!.name)
    }

    // Hilfsfunktion zum Maskieren von Sonderzeichen in Kotlin-Strings
    private fun escapeKotlinString(value: String?): String {
        if (value == null) return ""
        return value
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("$", "\\$")
            .trim()
    }

    fun generateCountryEnum() {
        val rows = URI("https://datahub.io/core/un-locode/_r/-/data/country-codes.csv")
            .toURL().readText().replace("\r\n", "\n").replace("\r", "\n").trim().split("\n")
        val data = rows.drop(1)
            .joinToString(",\n") { row ->
                val key = row.substringBefore(",")
                val value = row.substringAfter(",").removeSurrounding("\"")
                "    $key(\"$key\", \"${escapeKotlinString(value)}\")"
            }

        val code = """package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode

enum class Country(
    val prefix: String,
    val countryName: String
) {
$data
    ;
    companion object {
        fun fromPrefix(prefix: String): Country? = entries.find { e -> e.prefix == prefix }
    }
}
"""
        val targetFile = File("./src/commonMain/kotlin/de/visualdigits/shipermansfriend/domain/model/geodata/unlocode/Country.kt").canonicalFile
        targetFile.parentFile.mkdirs()
        targetFile.writeText(code)
    }

    val PATTERN_QUOTED = "\"(.*?)\"".toRegex()

    fun generatePortDataFiles() {
        val rows = URI("https://datahub.io/core/un-locode/_r/-/data/code-list.csv")
            .toURL().readText().replace("\r\n", "\n").replace("\r", "\n").trim().split("\n")
        val targetDirectory = File("./src/commonMain/kotlin/de/visualdigits/shipermansfriend/domain/model/geodata/unlocode/ports").canonicalFile
        targetDirectory.mkdirs()

        val attributes = rows.first().split(",").map { a -> a.lowercase() }
        val portData = rows
            .drop(1)
            .map { row ->
                if (row.contains("\"")) {
                    row
                        .replace(PATTERN_QUOTED) { match -> match.groups[1]?.value?.replace(",", ";") ?: "" }
                        .split(",")
                        .mapIndexed { index, value -> Pair(attributes[index], value.replace(";", ",")) }
                        .toMap()
                } else {
                    row
                        .split(",")
                        .mapIndexed { index, value -> Pair(attributes[index], value) }
                        .toMap()
                }
            }
            .filter { row -> row["function"]?.startsWith("1") == true && row["location"]?.isNotBlank() == true }
            .groupBy { row -> row["country"]?:"" }

        portData.forEach { country, entries ->
            val data = entries.joinToString(",\n") { entry ->
                val loc = escapeKotlinString(entry["location"])
                val name = escapeKotlinString(entry["name"])
                val location = entry["coordinates"]?.toLocation()?.let { l -> "Location(${(l.latitude * 1000000.0).roundToInt() / 1000000.0}, ${(l.longitude * 1000000.0).roundToInt() / 1000000.0})" }?:"null"
                "        PortCode(\"$country\", \"$loc\", \"$name\", $location)"
            }
            val code = """package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports

import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.PortCode
import de.visualdigits.common.domain.model.geodata.Location
                 
object Ports$country {
    val PORTS: List<PortCode> = listOf(
$data
    )
}
"""
            File(targetDirectory, "Ports$country.kt").writeText(code)
        }

        // --- OPTIMIERUNG FÜR PORTREGISTRY ---
        // Statt alle 240 Klassen in einer gigantischen Map hartzucoden, lagern wir das Laden
        // in eine Funktion aus. Das verhindert Methoden-Größenlimits in PortRegistry.kt komplett!
        val loadCases = portData.keys.sorted().joinToString("\n") { country ->
            "                \"$country\" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.Ports$country.PORTS.associateBy { it.code }"
        }

        val allCountriesList = portData.keys.sorted().joinToString(", ") { "\"$it\"" }

        val code = """package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode

object PortRegistry {

    // Cache für bereits geladene Länder, um Performance zu sichern
    private val loadedCountries = mutableMapOf<String, Map<String, PortCode>>()

    fun findPort(code: String?): PortCode? {
        if (code == null) return null
        
        val normalizedCode = code.trim().uppercase()
        return when (normalizedCode.length) {
            5 -> {
                val country = normalizedCode.take(2)
                val port = normalizedCode.substring(2)
                getOrLoadCountry(country)?.get(port)
            }
            3 -> {
                // Sucht dynamisch in allen Ländern (Lazy Loading)
                Country.entries.forEach { country ->
                    getOrLoadCountry(country.prefix)?.get(normalizedCode)?.let { return it }
                }
                null
            }
            else -> null
        }
    }
    
    private fun getOrLoadCountry(countryCode: String): Map<String, PortCode>? {
        return loadedCountries.getOrPut(countryCode) {
            when (countryCode) {
$loadCases
                else -> emptyMap()
            }
        }
    }
}
"""
        val targetFile = File("./src/commonMain/kotlin/de/visualdigits/shipermansfriend/domain/model/geodata/unlocode/PortRegistry.kt").canonicalFile
        targetFile.writeText(code)
    }
}
