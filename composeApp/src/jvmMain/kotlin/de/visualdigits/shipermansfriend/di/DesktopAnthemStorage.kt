package de.visualdigits.shipermansfriend.di

import de.visualdigits.compose.resources.Res

class DesktopAnthemStorage(private val homeDirectory: String) : AnthemStorage {

    override suspend fun prepareAnthem(countryCode: String): String? = runCatching {
        val cc = countryCode.lowercase()
        val bytes = Res.readBytes("files/$cc.mp3")
        val tempFile = java.io.File(homeDirectory, "temp_anthem_$cc.mp3")
        if (!tempFile.exists()) {
            tempFile.writeBytes(bytes)
        }

        tempFile.toURI().toString()
    }.getOrNull()
}
