package de.visualdigits.shipermansfriend.presentation.util

import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.errorhandling.LogMessage.Companion.log

fun routePlatformLink(uri: String) {
    val rawInput = uri.trim()
        .removeSurrounding("\\\"")
        .removeSurrounding("\"")
        .removeSurrounding("'")
        .trim()

    if (rawInput.startsWith("mailto:", ignoreCase = true) || rawInput.contains("@")) {
        sendEmail(
            emailAddress = rawInput.removePrefix("mailto:"),
            subject = "ShipermansFriend Support"
        )
    } else if (rawInput.startsWith("http://", ignoreCase = true) || rawInput.startsWith("https://", ignoreCase = true)) {
        openBrowser(url = rawInput)
    } else {
        log(Severity.Warn, "Unknown link format: $rawInput", withTag = "AIS")
    }
}

expect fun openBrowser(url: String)

expect fun sendEmail(emailAddress: String, subject: String, body: String? = null)

expect fun String.urlEncode(): String
