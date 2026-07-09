package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.NavigationalStatus
import kotlinx.serialization.Serializable
import kotlin.math.absoluteValue
import kotlin.math.sqrt

@Serializable
sealed interface PositionAisMessageData : AisMessageData {

    val location: Location
    val sog: Double
    val cog: Double
    val navigationalStatus: NavigationalStatus
    val rateOfTurn: Long
    val trueHeading: Long
    val isMoored: Boolean
    val timestamp: Long

    val displayHeading: Double
        get() {
            // 511 code for unavailable
            return if (trueHeading != 511L) {
                trueHeading.toDouble()
            } else {
                // fallback: If there is no compass aboard
                // If we see 360.0 the fallback is also not available
                if (cog >= 360.0) 0.0 else cog
            }
        }

    val rateOfTurnDegreesPerMinute: Double
        get() {
            return if (rateOfTurn == 128L) {
                10.0
            } else if (rateOfTurn == -128L) {
                -10.0
            } else if (rateOfTurn == 128L) {
                0.0
            } else {
                val factor = if (rateOfTurn < 0) -1.0 else 1.0
                factor * 4.733 * sqrt(rateOfTurn.toDouble().absoluteValue)
            }
        }
}
