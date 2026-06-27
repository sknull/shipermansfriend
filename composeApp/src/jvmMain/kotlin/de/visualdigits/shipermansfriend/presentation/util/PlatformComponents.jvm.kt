package de.visualdigits.shipermansfriend.presentation.util

import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.errorhandling.LogMessage.Companion.log
import java.awt.Desktop
import java.net.URI

actual fun routePlatformLink(uri: String) {
    try {
        if (!Desktop.isDesktopSupported()) return
        val desktop = Desktop.getDesktop()
        val rawInput = uri.trim()
            .removeSurrounding("\\\"")
            .removeSurrounding("\"")
            .removeSurrounding("'")
            .trim()

        if (rawInput.startsWith("mailto:", ignoreCase = true) || rawInput.contains("@")) {
            val cleanEmail = rawInput.substringAfter("mailto:")
            if (desktop.isSupported(Desktop.Action.MAIL)) {
                desktop.mail(URI("mailto:$cleanEmail"))
            }
        } else {
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(URI(rawInput))
            }
        }
    } catch (e: Exception) {
        // Safe protection against headless system architectures
        log(Severity.Info, "Could not rout uri for address '$uri'", e, withTag = "AIS")
    }
}
