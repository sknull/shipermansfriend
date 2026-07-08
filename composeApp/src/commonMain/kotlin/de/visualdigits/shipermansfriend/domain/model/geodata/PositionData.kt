package de.visualdigits.shipermansfriend.domain.model.geodata

import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType

class PositionData(
    messageType: MessageType,
    name: String,
    mmsi: Long,
    timeUtc: KmpOffsetDateTime,
    val location: Location,
    val sog: Double,
    val heading: Double,
    val rateOfTurnDegreesPerMinute: Double,
    val navigationalStatus: NavigationalStatus
) : AisData(
    messageType,
    name,
    mmsi,
    timeUtc
) {
    override fun toString(): String {
        return "PositionData(messageType='$messageType', name='$name', mmsi=$mmsi, timeUtc=$timeUtc, location=$location, sog=$sog, heading=$heading)"
    }

    val isMoored: Boolean
        get() = sog < 0.5
}

