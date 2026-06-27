package de.visualdigits.shipermansfriend.presentation.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.errorhandling.LogMessage.Companion.log
import org.koin.mp.KoinPlatformTools

actual fun routePlatformLink(uri: String) {
    try {
        val currentActivity = KoinPlatformTools.defaultContext().get().getOrNull<Activity>()
        if (currentActivity == null) {
            log(Severity.Error, "Could not resolve active Android Activity from Koin.", withTag = "UI")
            return
        }

        val rawInput = uri.trim()
            .removeSurrounding("\\\"")
            .removeSurrounding("\"")
            .removeSurrounding("'")
            .trim()

        // DETECT AND ROUTE EMAIL LINKS
        if (rawInput.startsWith("mailto:", ignoreCase = true) || rawInput.contains("@")) {
            // Clean up the string (remove mailto: if present, to prevent duplicates)
            val cleanEmail = rawInput.substringAfter("mailto:")
            val encodedEmail = Uri.encode(cleanEmail)

            // Unblockable mailto URI with subject to bypass strict tablet intent filters
            val emailUri = "mailto:$encodedEmail?subject=${Uri.encode("ShipermansFriend Support")}"

            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = emailUri.toUri()
            }
            currentActivity.startActivity(emailIntent)
            log(Severity.Info, "Routed email link safely: $cleanEmail", withTag = "UI")
        }
        // DETECT AND ROUTE STANDARD WEB LINKS
        else if (rawInput.startsWith("http://", ignoreCase = true) || rawInput.startsWith("https://", ignoreCase = true)) {
            val browserIntent = Intent(Intent.ACTION_VIEW).apply {
                data = rawInput.toUri()
            }
            currentActivity.startActivity(browserIntent)
            log(Severity.Info, "Routed browser link safely: $rawInput", withTag = "UI")
        }
        else {
            log(Severity.Warn, "Unknown link format encountered: $rawInput", withTag = "UI")
        }

    } catch (e: Exception) {
        log(Severity.Error, "Failed to route platform link ($uri): ${e.message}", e, withTag = "UI")
    }
}
