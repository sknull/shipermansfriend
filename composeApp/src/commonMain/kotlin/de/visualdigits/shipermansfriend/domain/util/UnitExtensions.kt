package de.visualdigits.shipermansfriend.domain.util

import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

private const val KILOMETERS_PER_HOUR = 1.852

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

fun Duration.formatTime(): String {
    var duration = this
    val days = this.inWholeDays
    duration -= days.days
    val hours = duration.inWholeHours
    duration -= hours.hours
    val minutes = duration.inWholeMinutes
    duration -= minutes.minutes
    val seconds = duration.inWholeSeconds

    val sdays = if (days > 0) "${days}d" else ""
    val shours = if (hours > 0) "${hours}h" else ""
    val sminutes = if (minutes > 0) "${minutes}m" else ""
    val sseconds = if (seconds > 0) "${seconds}s" else ""
    val string = listOf(sdays, shours, sminutes, sseconds).filter { it.isNotEmpty() }.joinToString(" ")

    return if (!string.isEmpty()) string else "0s"
}
