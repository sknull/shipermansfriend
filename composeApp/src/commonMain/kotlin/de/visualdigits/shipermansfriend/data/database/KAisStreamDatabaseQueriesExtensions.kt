package de.visualdigits.shipermansfriend.data.database

import de.visualdigits.shipermansfriend.ShipermansFriendDatabaseQueries
import de.visualdigits.shipermansfriend.SettingsEntity

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
