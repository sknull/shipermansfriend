package de.visualdigits.shipermansfriend.data.database

import de.visualdigits.shipermansfriend.PhotoProtocolEntryEntity
import de.visualdigits.shipermansfriend.SettingsEntity
import de.visualdigits.shipermansfriend.ShipermansFriendDatabaseQueries

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
        radiusOuter = masterDataEntity.radiusOuter,
        radiusInner = masterDataEntity.radiusInner,
        id = masterDataEntity.id
    )
}

fun ShipermansFriendDatabaseQueries.upsertPhotoProtocolEntryEntity(entry: PhotoProtocolEntryEntity) {
    upsertPhotoProtocolEntryEntity(
        timeUtc = entry.timeUtc,
        timeUtcObserved = entry.timeUtcObserved,
        observingLatitude = entry.observingLatitude,
        observingLongitude = entry.observingLongitude,
        shipType = entry.shipType,
        name = entry.name,
        mmsi = entry.mmsi,
        mmsiDeviceType = entry.mmsiDeviceType,
        mmsiCountry = entry.mmsiCountry,
        callSign = entry.callSign,
        imoNumber = entry.imoNumber,
        messageType = entry.messageType,
        speedOverGround = entry.speedOverGround,
        speedKmh = entry.speedKmh,
        heading = entry.heading,
        rateOfTurnDegreesPerMinute = entry.rateOfTurnDegreesPerMinute,
        navigationalStatus = entry.navigationalStatus,
        destination = entry.destination,
        totalLength = entry.totalLength,
        totalWidth = entry.totalWidth,
        maximumStaticDraught = entry.maximumStaticDraught,
        vesselLatitude = entry.vesselLatitude,
        vesselLongitude = entry.vesselLongitude,
        distance = entry.distance
    )
}
