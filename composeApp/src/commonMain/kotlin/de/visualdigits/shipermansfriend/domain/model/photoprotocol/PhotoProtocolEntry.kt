package de.visualdigits.shipermansfriend.domain.model.photoprotocol

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType
import de.visualdigits.shipermansfriend.domain.model.geodata.mmsi.MmsiCountry
import de.visualdigits.shipermansfriend.domain.model.geodata.mmsi.MmsiDeviceType
import de.visualdigits.shipermansfriend.domain.util.formatDistance

data class PhotoProtocolEntry(
    val timeUtc: String,
    val timeUtcObserved: String?,
    val observingLocation: Location?,
    val shipType: ShipType,
    val name: String,
    val mmsi: Long,
    val mmsiDeviceType: MmsiDeviceType,
    val mmsiCountry: MmsiCountry,
    val callSign: String?,
    val imoNumber: Long?,
    val messageType: MessageType,
    val speedOverGround: Double,
    val speedKmh: String,
    val heading: Double,
    val destination: String?,
    val totalLength: Long?,
    val totalWidth: Long?,
    val maximumStaticDraught: Double?,
    val vesselLocation: Location,
    val distance: Double
) {
    companion object {

        fun csvTitleRow(): String {
            return "timeUtcObserved;observingLocation;shipCategory;name;mmsi;deviceType;country;callSign;imoNumber;messageType;speedOverGroundKnots;speedOverGroundKmh;heading;destination;totalLength;totalWidth;maximumStaticDraught;vesselLocation;distance"
        }
    }

    fun toCsvRow(): String {
        return "${timeUtcObserved?.format("dd.MM.yyyy HH:mm:ss")};${observingLocation?.toDmsString()?:""};${shipType?.category?.name?:""};$name;$mmsi;$mmsiDeviceType;$mmsiCountry;${callSign?:""};${imoNumber?:""};${messageType.name};$speedOverGround;$speedKmh;$heading;${destination?:""};${totalLength?:""};${totalWidth?:""};${maximumStaticDraught?:""};${vesselLocation.toDmsString()};${distance.formatDistance()}"
    }
}
