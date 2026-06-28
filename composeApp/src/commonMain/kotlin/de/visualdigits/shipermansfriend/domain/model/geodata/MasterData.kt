package de.visualdigits.shipermansfriend.domain.model.geodata

import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType

class MasterData(
    messageType: MessageType,
    name: String,
    mmsi: Long,
    timeUtc: KmpOffsetDateTime,
    val imoNumber: Long,
    val callSign: String,
    val destination: String,
    val totalLength: Long,
    val totalWidth: Long,
    val shipType: ShipType,
    val maximumStaticDraught: Double
) : AisData(
    messageType,
    name,
    mmsi,
    timeUtc
) {
    override fun toString(): String {
        return "MasterData(messageType='$messageType', name='$name', mmsi=$mmsi, timeUtc=$timeUtc, imoNumber=$imoNumber, callSign='$callSign', destination='$destination', totalLength=$totalLength, totalWidth=$totalWidth, shipType=$shipType, maximumStaticDraught=$maximumStaticDraught)"
    }
}
