package de.visualdigits.shipermansfriend.di

import android.content.Context
import de.visualdigits.compose.resources.Res
import java.io.File

class AndroidAudioStorage(private val context: Context) : AudioStorage {

    override suspend fun prepareAudio(fileName: String): String? = runCatching {
        val bytes = Res.readBytes("files/$fileName")
        val tempFile = File(context.cacheDir, "temp_$fileName")
        if (!tempFile.exists()) {
            tempFile.writeBytes(bytes)
        }

        tempFile.absolutePath
    }.getOrNull()
}
