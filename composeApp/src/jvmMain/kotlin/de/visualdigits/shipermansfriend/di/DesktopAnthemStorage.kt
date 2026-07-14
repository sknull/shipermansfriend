package de.visualdigits.shipermansfriend.di

import de.visualdigits.compose.resources.Res

class DesktopAnthemStorage(private val homeDirectory: String) : AnthemStorage {
    override suspend fun prepareAnthem(countryCode: String): String? = runCatching {
        val cc = countryCode.uppercase()
        val resourcePath = "files/$cc.mp3"
        val bytes = Res.readBytes(resourcePath)

        val tempFile = java.io.File(homeDirectory, "temp_anthem_$cc.mp3")
        if (!tempFile.exists()) {
            tempFile.writeBytes(bytes)
        }

        tempFile.toURI().toString()
    }.getOrNull()
}
