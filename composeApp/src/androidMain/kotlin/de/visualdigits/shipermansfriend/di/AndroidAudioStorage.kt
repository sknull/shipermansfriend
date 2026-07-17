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

                // Do not readbytes in the android context because this does not work in production apk's
                // due to the way an apk file is assembled. Instead we use the classloader to fetch the resource from
                // the classpath.
                val stream = this::class.java.classLoader?.getResourceAsStream("assets/composeResources/de.visualdigits.compose.resources/files/$fileName")
                    ?: throw java.io.FileNotFoundException("Could not find resource: $fileName")

                stream.use { input ->
                    tempFile.outputStream().use { output ->
                        input.copyTo(output)
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
