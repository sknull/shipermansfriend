package de.visualdigits.shipermansfriend.domain.model.geodata

import de.visualdigits.shipermansfriend.domain.model.serializer.SpecialManoeuvreIndicatorSerializer
import kotlinx.serialization.Serializable

@Serializable(with = SpecialManoeuvreIndicatorSerializer::class)
enum class SpecialManoeuvreIndicator(
    val code: Long,
    val description: String
) {

    NOT_AVAILABLE(0, "not available = default"),
    NOT_ENGAGED(1, "not engaged in special maneuver"),
    ENGAGED(2, "engaged in special maneuver")
    ;

    companion object {

        fun fromCode(code: Long?): SpecialManoeuvreIndicator  = entries.find { it.code == code } ?: NOT_AVAILABLE
    }
}
