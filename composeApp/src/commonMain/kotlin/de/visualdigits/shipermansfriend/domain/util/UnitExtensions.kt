package de.visualdigits.shipermansfriend.domain.util

import de.visualdigits.shipermansfriend.domain.model.geodata.KILOMETERS_PER_HOUR
import kotlin.math.roundToInt


/**
 * Formats this distanz in meters in human-readable form (i.e. "350 m" or "4.2 km").
 */
fun Double.formatDistance(): String {
    return if (this < 1000.0) {
        "${this.roundToInt()} m"
    } else {
        val km = this / 1000.0
        val roundedKm = (km * 10).roundToInt() / 10.0
        "$roundedKm km"
    }
}

private val P_DOUBLE = "\\d+(?:\\.\\d+)?".toRegex()

fun String.parseDistance(): Double {
    val s = this.trim().lowercase()
    val match = P_DOUBLE.find(s)
    val value = match?.value?.toDouble() ?: 0.0
    return if (s.endsWith("km")) {
        value * 1000.0
    } else {
        value
    }
}

/**
 * Formats this distanz in meters in human-readable form (i.e. "350 m" or "4.2 km").
 */
fun Double.formatSpeed(): String {
    val kmh = this  * KILOMETERS_PER_HOUR
    val roundedKmh = (kmh * 10).roundToInt() / 10.0
    return "$roundedKmh Km/h"
}
