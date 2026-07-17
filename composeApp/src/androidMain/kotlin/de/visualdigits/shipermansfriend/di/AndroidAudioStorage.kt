package de.visualdigits.shipermansfriend.di

import android.content.Context
import co.touchlab.kermit.Logger
import java.io.File

class AndroidAudioStorage(private val context: Context) : AudioStorage {

    override suspend fun prepareAudio(fileName: String): String? {
        return try {
            val tempFile = File(context.cacheDir, "temp_$fileName")
            Logger.i("Prepare audio '$fileName', tempFile: '$tempFile'")

            if (!tempFile.exists()) {
                Logger.i("Cached audio '$fileName', tempFile: '$tempFile'")

                val assetManager = context.assets
                val primaryPath = "composeResources/de.visualdigits.compose.resources/files/$fileName"
                val fallbackPath = "composeResources/files/$fileName"
                val inputStream = try {
                    assetManager.open(primaryPath)
                } catch (e: java.io.FileNotFoundException) {
                    Logger.i("Primary path not found, trying fallback path for '$fileName'")
                    assetManager.open(fallbackPath)
                }
                inputStream.use { ins ->
                    tempFile.outputStream().use { outs ->
                        ins.copyTo(outs)
                    }
                }
            }

            tempFile.absolutePath
        } catch (e: Exception) {
            Logger.e("FAILED to prepare audio '$fileName': ${e.message}", e)
            null
        }
    }
}
