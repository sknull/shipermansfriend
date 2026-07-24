package de.visualdigits.shipermansfriend.di

import co.touchlab.kermit.Logger
import de.visualdigits.compose.resources.Res
import java.io.File

class DesktopAudioStorage(private val homeDirectory: String) : AudioStorage {

    override suspend fun prepareAudio(fileName: String?): String? {
        if (fileName == null) return null
        val cacheDirectory =  File(homeDirectory, "audio")
        val finalCacheDirectory = if (!cacheDirectory.exists()) {
            if (!cacheDirectory.mkdirs()) {
                Logger.w("Could not create audio cache directory - fall back to homedirectory")
                File(homeDirectory)
            } else {
                cacheDirectory
            }
        } else {
            cacheDirectory
        }
        return try {
            val tempFile = File(finalCacheDirectory, "temp_$fileName")
            Logger.d("Prepare audio '$fileName', tempFile: '$tempFile'")
            if (!tempFile.exists()) {
                Logger.d("Cached audio '$fileName', tempFile: '$tempFile'")
                val bytes = Res.readBytes("files/$fileName")
                tempFile.writeBytes(bytes)
            }

            tempFile.toURI().toString()
        } catch (e: Exception) {
            Logger.e("Could not cache audio '$fileName'", e)
            null
        }
    }
}
