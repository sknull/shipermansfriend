package de.visualdigits.shipermansfriend.data.database

import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.configuration.AbstractConfiguration.Companion.valueMap
import de.visualdigits.common.domain.model.configuration.keyfactory.BooleanEnum
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.MasterDataEntity
import de.visualdigits.shipermansfriend.SettingsEntity
import de.visualdigits.shipermansfriend.StarredVesselEntity
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.MasterData
import de.visualdigits.shipermansfriend.domain.model.geodata.NavigationalStatus
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType
import de.visualdigits.shipermansfriend.domain.model.geodata.mmsi.MmsiCountry
import de.visualdigits.shipermansfriend.domain.model.geodata.mmsi.MmsiCountryEurope
import de.visualdigits.shipermansfriend.domain.model.geodata.mmsi.MmsiCountryPrefix
import de.visualdigits.shipermansfriend.domain.model.geodata.mmsi.MmsiDeviceType
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
        warningDistance = get<String>(SK.warningDistance) ?: "10km",
        radiusOuter = get<String>(SK.radiusOuter) ?: "2km",
        radiusInner = get<String>(SK.radiusInner) ?: "1km",
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
                SK.warningDistance to warningDistance,
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

fun AisDataUi.toStarredVesselEntity(): StarredVesselEntity {
    return StarredVesselEntity(
        messageType = messageType.name,
        name = name,
        mmsi = mmsi,
        mmsiDeviceType = mmsiCountryPrefix.deviceType.name,
        mmsiCountry = mmsiCountryPrefix.country.countryCode,
        timeUtc = timeUtc.toString(),
        timeUtcObserved = timeUtcObserved.toString(),
        vesselLatitude = location.latitude,
        vesselLongitude = location.longitude,
        observingLatitude = observingLocation?.latitude,
        observingLongitude = observingLocation?.longitude,
        sog = sog,
        speedKmh = speedKmh,
        heading = heading,
        rateOfTurnDegreesPerMinute = rateOfTurnDegreesPerMinute,
        navigationalStatus = navigationalStatus.code,
        imoNumber = imoNumber,
        callSign = callSign,
        destination = destination,
        totalLength = totalLength,
        totalWidth = totalWidth,
        shipType = shipType.code,
        maximumStaticDraught = maximumStaticDraught,
        distance = distance,
        hasSafetyMessage = hasSafetyMessage,
        messageId = messageId,
        repeatIndicator = repeatIndicator,
        valid = valid,
        text = text
    )
}

fun StarredVesselEntity.toAisDataUi(): AisDataUi {
    return AisDataUi(
        messageType = MessageType.valueOf(messageType),
        name = name,
        mmsi = mmsi,
        mmsiCountryPrefix = MmsiCountryPrefix(MmsiDeviceType.valueOf(mmsiDeviceType), MmsiCountry.fromCountryCode(mmsiCountry)?: MmsiCountryEurope.COUNTRY_UNKNOWN),
        timeUtc = KmpOffsetDateTime.fromString(timeUtc),
        timeUtcObserved = timeUtcObserved.let { t -> KmpOffsetDateTime.fromString(t) },
        location = Location(vesselLatitude, vesselLongitude),
        observingLocation = if (observingLatitude != null && observingLongitude != null) Location(observingLatitude, observingLongitude) else null,
        sog = sog,
        speedKmh = speedKmh,
        heading = heading,
        rateOfTurnDegreesPerMinute = rateOfTurnDegreesPerMinute,
        navigationalStatus = NavigationalStatus.fromCode(navigationalStatus),
        imoNumber = imoNumber,
        callSign = callSign,
        destination = destination,
        totalLength = totalLength,
        totalWidth = totalWidth,
        shipType = ShipType.fromCode(shipType) ?: ShipType.Unknown_0,
        maximumStaticDraught = maximumStaticDraught,
        distance = distance,
        hasSafetyMessage = hasSafetyMessage,
        messageId = messageId,
        repeatIndicator = repeatIndicator,
        valid = valid,
        text = text
    )
}
