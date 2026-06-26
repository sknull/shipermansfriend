package de.visualdigits.shipermansfriend.domain.util

private val P_DURATION = "(\\d+?)M(\\d+?)S".toRegex()
private val P_MINUTES = "(\\d+?)M".toRegex()
private val P_SECONDS = "(\\d+?)S".toRegex()

/**
 * extract domain for googles favicon service
 */
fun String.getFaviconUrl(sizePx: Int): String {
    val domain = this
        .substringAfter("://")   // Entfernt http:// oder https://
        .substringBefore("/")    // Entfernt alle Pfade am Ende
        .removePrefix("www.")    // Optional, aber macht die Domain sauberer

    return "https://www.google.com/s2/favicons?domain=${domain}&sz=${sizePx}"
}
