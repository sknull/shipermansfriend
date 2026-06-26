package de.visualdigits.shipermansfriend.data.database

import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.configuration.AbstractConfiguration.Companion.valueMap
import de.visualdigits.common.domain.model.configuration.keyfactory.BooleanEnum
import de.visualdigits.shipermansfriend.MasterDataEntity
import de.visualdigits.shipermansfriend.SettingsEntity
import de.visualdigits.shipermansfriend.domain.model.geodata.MasterData
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType
import de.visualdigits.shipermansfriend.domain.model.settings.SK
import de.visualdigits.shipermansfriend.domain.model.settings.Settings
import de.visualdigits.shipermansfriend.domain.model.type.Language
import kotlinx.serialization.json.Json

private val mapper = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
    encodeDefaults = true
}

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
        mmsi = mmsi,
        name = name,
        timeUtc = KmpOffsetDateTime.fromString(timeUtc),
        imoNumber = imoNumber,
        callSign = callSign,
        destination = destination,
        totalLength = totalLength,
        totalWidth = totalWidth,
        shipType = ShipType.fromValue(shipType) ?: ShipType.Unknown_0,
        maximumStaticDraught = maximumStaticDraught
    )
}
