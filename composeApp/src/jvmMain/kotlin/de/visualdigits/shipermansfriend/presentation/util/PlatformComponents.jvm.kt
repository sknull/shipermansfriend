package de.visualdigits.shipermansfriend.presentation.util

import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.errorhandling.LogMessage.Companion.log
import java.awt.Desktop
import java.net.URI
import java.net.URLEncoder

actual fun openBrowser(url: String) {
    try {
        if (!Desktop.isDesktopSupported()) return
        val desktop = Desktop.getDesktop()
        if (desktop.isSupported(Desktop.Action.BROWSE)) {
            desktop.browse(URI(url))
        } else {
            log(Severity.Warn, "Browsing is not supported", withTag = "AIS")
        }
    } catch (e: Exception) {
        log(Severity.Error, "Failed to open browser for link: $url", e, withTag = "AIS")
    }
}

actual fun sendEmail(emailAddress: String, subject: String, body: String?) {
    try {
        if (!Desktop.isDesktopSupported()) {
            return
        }
        val desktop = Desktop.getDesktop()

        val encodedEmailAddress = emailAddress.urlEncode()
        val encodeSubject = subject.urlEncode()
        val encodeBody = body?.urlEncode()
        val bodyParam = encodeBody?.let { b -> "&body=$b"} ?: ""
        val emailUri = "mailto:$encodedEmailAddress?subject=$encodeSubject$bodyParam"

        if (desktop.isSupported(Desktop.Action.MAIL)) {
            desktop.mail(URI(emailUri))
            log(Severity.Info, "Opened email link: $emailAddress", withTag = "AIS")
        } else {
            log(Severity.Warn, "Email is not supported", withTag = "AIS")
        }
    } catch (e: Exception) {
        log(Severity.Error, "Failed to open email link emailAddress ($emailAddress)", e, withTag = "AIS")
    }
}

actual fun String.urlEncode(): String = URLEncoder.encode(this, Charsets.UTF_8).replace("+", "%20")
