package de.visualdigits.shipermansfriend.data.database

import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.configuration.AbstractConfiguration.Companion.valueMap
import de.visualdigits.common.domain.model.configuration.keyfactory.BooleanEnum
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.MasterDataEntity
import de.visualdigits.shipermansfriend.SettingsEntity
import de.visualdigits.shipermansfriend.StarredVesselEntity
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType
import de.visualdigits.shipermansfriend.domain.model.geodata.NavigationalStatus
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType
import de.visualdigits.shipermansfriend.domain.model.geodata.mmsi.MasterData
import de.visualdigits.shipermansfriend.domain.model.geodata.mmsi.MmsiCountry.Companion.fromCountryCode
import de.visualdigits.shipermansfriend.domain.model.geodata.mmsi.MmsiCountryEurope
import de.visualdigits.shipermansfriend.domain.model.geodata.mmsi.MmsiDeviceType
import de.visualdigits.shipermansfriend.domain.model.starredvessels.StarredVessel
import de.visualdigits.shipermansfriend.domain.model.settings.SK
import de.visualdigits.shipermansfriend.domain.model.settings.Settings
import de.visualdigits.shipermansfriend.domain.model.type.Language

fun Settings.toSettingsEntity(): SettingsEntity {
    val settingsEntity = SettingsEntity(
        id = 0,
        language = get<Language>(SK.language)?.localeCode ?: "en",
        lastMaxImageSize = get<Int>(SK.maxImageSize)?.toLong() ?: 1200L,
        aisstreamApiKey = get<String>(SK.aisstreamApiKey) ?: "",
        location = get<String>(SK.location) ?: "",
        useGpsLocation = get<BooleanEnum>(SK.useGpsLocation)?.booleanValue ?: false,
        radiusOuter = get<String>(SK.radiusOuter) ?: "",
        radiusInner = get<String>(SK.radiusInner) ?: "",
    )
    return settingsEntity
}

fun SettingsEntity.toSettings(): Settings {
    return Settings(
        valueMap(
            fieldDescriptors = Settings.DESCRIPTORS,
            values = mapOf(
                SK.language to Language.fromValue(language),
                SK.maxImageSize to lastMaxImageSize,
                SK.aisstreamApiKey to aisstreamApiKey,
                SK.location to location,
                SK.useGpsLocation to BooleanEnum.fromValue(useGpsLocation),
                SK.radiusOuter to radiusOuter,
                SK.radiusInner to radiusInner
            )
        )
    )
}

fun MasterData.toMasterDataEntity(): MasterDataEntity {
    return MasterDataEntity(
        messageType = messageType.name,
        name = name,
        mmsi = mmsi,
        timeUtc = timeUtc.toString(),
        imoNumber = imoNumber,
        callSign = callSign,
        destination = destination,
        totalLength = totalLength,
        totalWidth = totalWidth,
        shipType = shipType.code,
        maximumStaticDraught = maximumStaticDraught
    )
}

fun MasterDataEntity.toMasterData(): MasterData {
    return MasterData(
        messageType = MessageType.valueOf(messageType),
        name = name,
        mmsi = mmsi,
        timeUtc = KmpOffsetDateTime.fromString(timeUtc),
        imoNumber = imoNumber,
        callSign = callSign,
        destination = destination,
        totalLength = totalLength,
        totalWidth = totalWidth,
        shipType = ShipType.fromCode(shipType) ?: ShipType.Unknown_0,
        maximumStaticDraught = maximumStaticDraught
    )
}

fun StarredVessel.toStarredVesselEntity(): StarredVesselEntity {
    return StarredVesselEntity(
        timeUtc = timeUtc,
        timeUtcObserved = timeUtcObserved,
        observingLatitude = observingLocation?.latitude,
        observingLongitude = observingLocation?.longitude,
        shipType = shipType.code,
        name = name,
        mmsi = mmsi,
        mmsiDeviceType = mmsiDeviceType.name,
        mmsiCountry = mmsiCountry.countryCode,
        callSign = callSign,
        imoNumber = imoNumber,
        messageType = messageType.name,
        speedOverGround = speedOverGround,
        speedKmh = speedKmh.toString(),
        heading = heading,
        rateOfTurnDegreesPerMinute = rateOfTurnDegreesPerMinute,
        navigationalStatus = navigationalStatus.code,
        destination = destination,
        totalLength = totalLength,
        totalWidth = totalWidth,
        maximumStaticDraught = maximumStaticDraught,
        vesselLatitude = vesselLocation.latitude,
        vesselLongitude = vesselLocation.longitude,
        distance = distance
    )
}

fun StarredVesselEntity.toStarredVessel(): StarredVessel {
    return StarredVessel(
        timeUtc = timeUtc,
        timeUtcObserved = timeUtcObserved,
        observingLocation = if (observingLatitude != null && observingLongitude != null) Location(observingLatitude, observingLongitude) else null,
        shipType = ShipType.fromCode(shipType) ?: ShipType.Unknown_0,
        name = name,
        mmsi = mmsi,
        mmsiDeviceType = MmsiDeviceType.valueOf(mmsiDeviceType),
        mmsiCountry = fromCountryCode(mmsiCountry) ?: MmsiCountryEurope.COUNTRY_UNKNOWN,
        callSign = callSign,
        imoNumber = imoNumber,
        messageType = MessageType.valueOf(messageType),
        speedOverGround = speedOverGround,
        speedKmh = speedKmh.split(" ").first().toDouble(),
        heading = heading,
        rateOfTurnDegreesPerMinute = rateOfTurnDegreesPerMinute,
        navigationalStatus = NavigationalStatus.fromCode(navigationalStatus),
        destination = destination,
        totalLength = totalLength,
        totalWidth = totalWidth,
        maximumStaticDraught = maximumStaticDraught,
        vesselLocation = Location(vesselLatitude, vesselLongitude),
        distance = distance
    )
}
