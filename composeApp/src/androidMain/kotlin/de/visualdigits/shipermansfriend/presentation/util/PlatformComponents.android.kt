package de.visualdigits.shipermansfriend.presentation.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.errorhandling.LogMessage.Companion.log
import org.koin.mp.KoinPlatformTools
import java.net.URLEncoder

actual fun openBrowser(url: String) {
    try {
        val currentActivity = KoinPlatformTools.defaultContext().get().getOrNull<Activity>()
        if (currentActivity == null) {
            log(Severity.Error, "Could not resolve active Android Activity from Koin.", withTag = "AIS")
            return
        }

        currentActivity.startActivity(Intent(Intent.ACTION_VIEW).apply {
            this.data = url.toUri()
        })
        log(Severity.Info, "Opened browser link: $url", withTag = "AIS")
    } catch (e: Exception) {
        log(Severity.Error, "Failed to open browser for link: $url", e, withTag = "AIS")
    }
}

actual fun sendEmail(emailAddress: String, subject: String, body: String?) {
    try {
        val currentActivity = KoinPlatformTools.defaultContext().get().getOrNull<Activity>()
        if (currentActivity == null) {
            log(Severity.Warn, "Could not resolve active Android Activity from Koin.", withTag = "AIS")
            return
        }

        val encodedEmailAddress = emailAddress.urlEncode()
        val encodeSubject = subject.urlEncode()
        val encodeBody = body?.urlEncode()
        val bodyParam = encodeBody?.let { b -> "&body=$b"} ?: ""
        val emailUri = "mailto:$encodedEmailAddress?subject=$encodeSubject$bodyParam"

        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = emailUri.toUri()
        }
        currentActivity.startActivity(emailIntent)
        log(Severity.Info, "Opened email link: $emailAddress", withTag = "AIS")
    } catch (e: Exception) {
        log(Severity.Error, "Failed to open email link emailAddress ($emailAddress)", e, withTag = "AIS")
    }
}

actual fun String.urlEncode(): String = Uri.encode(this)
