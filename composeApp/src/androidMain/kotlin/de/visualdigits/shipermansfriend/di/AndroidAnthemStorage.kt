package de.visualdigits.shipermansfriend.di

import android.content.Context
import de.visualdigits.compose.resources.Res
import java.io.File

class AndroidAnthemStorage(private val context: Context) : AnthemStorage {

    override suspend fun prepareAnthem(countryCode: String): String? = runCatching {
        val cc = countryCode.lowercase()
        val bytes = Res.readBytes("files/$cc.mp3")
        val tempFile = File(context.cacheDir, "temp_anthem_$cc.mp3")
        if (!tempFile.exists()) {
            tempFile.writeBytes(bytes)
        }

        tempFile.absolutePath
    }.getOrNull()
}
