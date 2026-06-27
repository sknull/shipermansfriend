package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.common.domain.model.geodata.Location
import kotlinx.serialization.Serializable

@Serializable
sealed interface PositionAisMessageData : AisMessageData {

    val location: Location
    val sog: Double
    val cog: Double
    val trueHeading: Int
    val isMoored: Boolean
    val timestamp: Int

    val displayHeading: Double
        get() {
            // 511 code for unavailable
            return if (trueHeading != 511) {
                trueHeading.toDouble()
            } else {
                // fallback: If there is no compass aboard
                // If we see 360.0 the fallback is also not available
                if (cog >= 360.0) 0.0 else cog
            }
        }
}
