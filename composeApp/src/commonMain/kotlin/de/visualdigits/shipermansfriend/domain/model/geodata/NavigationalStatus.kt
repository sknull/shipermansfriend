package de.visualdigits.shipermansfriend.domain.model.geodata

import de.visualdigits.shipermansfriend.domain.model.serializer.NavigationalStatusSerializer
import kotlinx.serialization.Serializable

@Serializable(with = NavigationalStatusSerializer::class)
enum class NavigationalStatus(
    val code: Long,
    val description: String
) {
    UNDER_WAY_USING_ENGINE(0, "under way using engine"),
    AT_ANCHOR(1, "at anchor"),
    NOT_UNDER_COMMAND(2, "not under command"),
    RESTRICTED_MANEUVERABILITY(3, "restricted maneuverability"),
    CONSTRAINED_BY_HER_DRAUGHT(4, "constrained by her draught"),
    MOORED(5, "moored"),
    AGROUND(6, "aground"),
    ENGAGED_IN_FISHING(7, "engaged in fishing"),
    UNDER_WAY_SAILING(8, "under way sailing"),
    AIS_SART_ACTIVE(14, "AIS-SART (active) MOB-AIS EPIRB-AIS"),
    UNDEFINED(15, "undefined = default (also used by AIS-SART MOB-AIS and EPIRB-AIS under test)")
    ;

    companion object {
        fun fromCode(code: Long?): NavigationalStatus = entries.find { it.code == code } ?: UNDEFINED
    }
}
