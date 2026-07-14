package de.visualdigits.shipermansfriend.di

import android.content.Context
import de.visualdigits.compose.resources.Res
import java.io.File

class AndroidAnthemStorage(private val context: Context) : AnthemStorage {

    override suspend fun prepareAnthem(countryCode: String): String? {
        val cc = countryCode.uppercase()
        return try {
            val resourcePath = "files/$cc.mp3"

            // 1. Schlägt das hier fehl? (MissingResourceException)
            val bytes = Res.readBytes(resourcePath)

            val tempFile = File(context.cacheDir, "temp_anthem_$cc.mp3")
            if (!tempFile.exists()) {
                tempFile.writeBytes(bytes)
            }

            // 3. Das saubere URL-Format für JavaFX erzwingen
            tempFile.absolutePath
        } catch (e: Exception) {
            println("Could not prepare anthem for countryCode '$cc'")
            println(e.stackTraceToString())
            null
        }
    }
}
