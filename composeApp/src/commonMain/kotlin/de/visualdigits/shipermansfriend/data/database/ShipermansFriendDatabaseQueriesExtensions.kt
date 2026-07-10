package de.visualdigits.shipermansfriend.data.database

import de.visualdigits.shipermansfriend.SettingsEntity
import de.visualdigits.shipermansfriend.ShipermansFriendDatabaseQueries
import de.visualdigits.shipermansfriend.StarredVesselEntity

fun ShipermansFriendDatabaseQueries.upsertSettings(masterDataEntity: SettingsEntity) {
    val entity = getSettingsById(masterDataEntity.id).executeAsOneOrNull()
    if (entity != null) {
        updateSettings(masterDataEntity)
    } else {
        insertSettings(masterDataEntity)
    }
}

fun ShipermansFriendDatabaseQueries.insertSettings(masterDataEntity: SettingsEntity) {
    insertSettings(
        language = masterDataEntity.language,
        lastMaxImageSize = masterDataEntity.lastMaxImageSize,
        aisstreamApiKey = masterDataEntity.aisstreamApiKey,
        location = masterDataEntity.location,
        useGpsLocation = masterDataEntity.useGpsLocation,
        warningDistance = masterDataEntity.warningDistance,
        radiusOuter = masterDataEntity.radiusOuter,
        radiusInner = masterDataEntity.radiusInner
    )
}

fun ShipermansFriendDatabaseQueries.updateSettings(masterDataEntity: SettingsEntity) {
    updateSettingsEntity(
        language = masterDataEntity.language,
        lastMaxImageSize = masterDataEntity.lastMaxImageSize,
        aisstreamApiKey = masterDataEntity.aisstreamApiKey,
        location = masterDataEntity.location,
        useGpsLocation = masterDataEntity.useGpsLocation,
        warningDistance = masterDataEntity.warningDistance,
        radiusOuter = masterDataEntity.radiusOuter,
        radiusInner = masterDataEntity.radiusInner,
        id = masterDataEntity.id
    )
}

fun ShipermansFriendDatabaseQueries.upsertStarredVesselEntity(entry: StarredVesselEntity) {
    upsertStarredVesselEntity(
        messageType = entry.messageType,
        name = entry.name,
        mmsi = entry.mmsi,
        mmsiDeviceType = entry.mmsiDeviceType,
        mmsiCountry = entry.mmsiCountry,
        timeUtc = entry.timeUtc,
        timeUtcObserved = entry.timeUtcObserved,
        vesselLatitude = entry.vesselLatitude,
        vesselLongitude = entry.vesselLongitude,
        observingLatitude = entry.observingLatitude,
        observingLongitude = entry.observingLongitude,
        sog = entry.sog,
        speedKmh = entry.speedKmh,
        heading = entry.heading,
        rateOfTurnDegreesPerMinute = entry.rateOfTurnDegreesPerMinute,
        navigationalStatus = entry.navigationalStatus,
        imoNumber = entry.imoNumber,
        callSign = entry.callSign,
        destination = entry.destination,
        totalLength = entry.totalLength,
        totalWidth = entry.totalWidth,
        shipType = entry.shipType,
        maximumStaticDraught = entry.maximumStaticDraught,
        distance = entry.distance,
        hasSafetyMessage = entry.hasSafetyMessage,
        messageId = entry.messageId,
        repeatIndicator = entry.repeatIndicator,
        valid = entry.valid,
        text = entry.text
    )
}
