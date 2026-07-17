package de.visualdigits.shipermansfriend.di

import android.content.Context
import co.touchlab.kermit.Logger
import de.visualdigits.compose.resources.Res
import java.io.File

class AndroidAudioStorage(private val context: Context) : AudioStorage {

    override suspend fun prepareAudio(fileName: String): String? {
        return try {
            val tempFile = File(context.cacheDir, "temp_$fileName")
            Logger.i("Prepare audio '$fileName', tempFile: '$tempFile'")
            if (!tempFile.exists()) {
                Logger.i("Cached audio '$fileName', tempFile: '$tempFile'")
                val bytes = Res.readBytes("files/$fileName")
                tempFile.writeBytes(bytes)
            }

            tempFile.absolutePath
        } catch (e: Exception) {
            Logger.e("Could not cache audio '$fileName'", e)
            null
        }
    }
}
