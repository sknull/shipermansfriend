package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import androidx.compose.runtime.Immutable
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.NavigationalStatus
import kotlinx.serialization.Serializable
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.sign

@Immutable
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

    val rateOfTurnDegreesPerMinute: Double?
        get() {
            return when (rateOfTurn) {
                -128L -> null     // No data available
                127L -> 720.0     // Fast right turn (>720°/min)
                -127L -> -720.0   // Fast left turn (>720°/min)
                0L -> 0.0         // Straight forward
                else -> {
                    // determine sign (negative = left, positive = right)
                    val sign = rateOfTurn.toDouble().sign

                    // decode raw data according to specs
                    val rawValue = rateOfTurn.toDouble().absoluteValue
                    val degreesPerMinute = (rawValue / 4.733).pow(2)

                    // reattach sign
                    sign * degreesPerMinute
                }
            }
        }
}
