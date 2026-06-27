package de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Dimension(
    @SerialName("A") val a: Long, // Abstand von der Antenne nach vorne bis zum Bug (Bow).
    @SerialName("B") val b: Long, // Abstand von der Antenne nach hinten bis zum Heck (Stern) - kann 0 sein wenn der Käptn alles in a eingetragen hat.
    @SerialName("C") val c: Long, // Abstand von der Antenne nach links zur Backbordseite (Port) - kann 0 sein wenn der Käptn alles in a eingetragen hat.
    @SerialName("D") val d: Long  // Abstand von der Antenne nach rechts zur Steuerbordseite (Starboard) - kann 0 sein wenn der Käptn alles in a eingetragen hat.
) {

    val totalLength: Long
        get() = if (a != 511L && b != 511L) a + b else 0

    val totalWidth: Long
        get() = if (c != 63L && d != 63L) c + d else 0
}
