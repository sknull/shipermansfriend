package de.visualdigits.shipermansfriend.domain.model.geodata

import de.visualdigits.common.domain.model.common.KmpOffsetDateTime

class MasterData(
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
    name,
    mmsi,
    timeUtc
) {
    override fun toString(): String {
        return "MasterData(name='$name', mmsi=$mmsi, timeUtc=$timeUtc, imoNumber=$imoNumber, callSign='$callSign', destination='$destination', totalLength=$totalLength, totalWidth=$totalWidth, shipType=$shipType, maximumStaticDraught=$maximumStaticDraught)"
    }
}
