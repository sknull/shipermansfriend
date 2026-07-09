package de.visualdigits.shipermansfriend.domain.mapper

import de.visualdigits.common.domain.model.common.KmpOffsetDateTimeHeuristicDeserializer
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.mmsi.MmsiCountryPrefix
import de.visualdigits.shipermansfriend.domain.model.starredvessels.StarredVessel
import de.visualdigits.shipermansfriend.domain.util.formatDistance

fun AisDataUi.toStarredVessel(location: Location?) : StarredVessel {
    return StarredVessel(
        timeUtc = timeUtc.toString(),
        timeUtcObserved = timeUtcObserved.toString(),
        observingLocation = location,
        shipType = shipType,
        name = name,
        mmsi = mmsi,
        mmsiDeviceType = mmsiCountryPrefix.deviceType,
        mmsiCountry = mmsiCountryPrefix.country,
        callSign = callSign,
        imoNumber = imoNumber,
        messageType = messageType,
        speedOverGround = sog,
        speedKmh = speedKmh,
        heading = heading,
        rateOfTurnDegreesPerMinute = rateOfTurnDegreesPerMinute,
        navigationalStatus = navigationalStatus,
        destination = destination,
        totalLength = totalLength,
        totalWidth = totalWidth,
        maximumStaticDraught = maximumStaticDraught,
        vesselLocation = this.location,
        distance = distance
    )
}

fun StarredVessel.toAisDataUi() : AisDataUi {
    return AisDataUi(
        timeUtc = KmpOffsetDateTimeHeuristicDeserializer.parse(timeUtc),
        timeUtcObserved = timeUtcObserved?.let { to -> KmpOffsetDateTimeHeuristicDeserializer.parse(to) },
        shipType = shipType,
        name = name,
        mmsi = mmsi,
        mmsiCountryPrefix = MmsiCountryPrefix(mmsiDeviceType, mmsiCountry),
        callSign = callSign,
        imoNumber = imoNumber,
        messageType = messageType,
        sog = speedOverGround,
        speedKmh = speedKmh,
        heading = heading,
        rateOfTurnDegreesPerMinute = rateOfTurnDegreesPerMinute,
        navigationalStatus = navigationalStatus,
        destination = destination,
        totalLength = totalLength,
        totalWidth = totalWidth,
        maximumStaticDraught = maximumStaticDraught,
        location = vesselLocation,
        distance = distance,
        distanceString = distance.formatDistance()
    )
}
