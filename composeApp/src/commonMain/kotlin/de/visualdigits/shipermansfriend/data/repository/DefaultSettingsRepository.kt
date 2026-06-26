package de.visualdigits.shipermansfriend.data.repository

import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.configuration.AbstractConfiguration.Companion.valueMap
import de.visualdigits.common.domain.model.errorhandling.LogMessage.Companion.log
import de.visualdigits.common.domain.model.errorhandling.Result
import de.visualdigits.common.domain.util.CryptoBox
import de.visualdigits.common.domain.util.EncryptedString
import de.visualdigits.shipermansfriend.ShipermansFriendDatabaseQueries
import de.visualdigits.shipermansfriend.data.database.toSettings
import de.visualdigits.shipermansfriend.data.database.toSettingsEntity
import de.visualdigits.shipermansfriend.data.database.upsertSettings
import de.visualdigits.shipermansfriend.domain.model.errorhandling.DataError
import de.visualdigits.shipermansfriend.domain.model.settings.SK
import de.visualdigits.shipermansfriend.domain.model.settings.Settings
import de.visualdigits.shipermansfriend.domain.model.type.Language
import de.visualdigits.shipermansfriend.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.Sink
import kotlinx.io.Source
import kotlinx.io.readString
import kotlinx.io.writeString
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonPrimitive

class DefaultSettingsRepository(
    private val dao: ShipermansFriendDatabaseQueries,
    private val cryptoBox: CryptoBox
): SettingsRepository {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun getSettings(): Result<Settings?, DataError .Local> = withContext(dispatcher) {
        try {
            dao.getSettingsById(0)
                .executeAsOneOrNull()
                ?.let { settingsEntity ->
                    settingsEntity
                        .toSettings()
                        .let { s ->
                            Result.Success(s)
                        }
                } ?: Result.Success(null)
        } catch (e: Exception) {
            log(Severity.Error, "Could not load settings", e, withTag = "AIS")
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun setSettings(settings: Settings): Result<Unit, DataError.Local> = withContext(dispatcher) {
        try {
            val settingsEntity = settings.toSettingsEntity()
            dao.upsertSettings(settingsEntity)
            Result.Success(Unit)
        } catch (e: Exception) {
            log(Severity.Error, "Could not set settings", e, withTag = "AIS")
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun importSettings(source: Source): Result<Settings, DataError.Local> = withContext(dispatcher) {
        try {
            val jsonMapper = Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            }
            val json = source.use { ins ->
                ins.readString()
            }

            val settings = Settings(
                valueMap(
                    fieldDescriptors = Settings.DESCRIPTORS,
                    values = jsonMapper
                        .decodeFromString<Map<String, JsonElement>>(json)
                        .mapNotNull { (key, value) ->
                            val sk = SK.fromString(key)
                            if (sk != null) {
                                val rawValue = value.jsonPrimitive.content
                                val finalValue = if (sk == SK.aisstreamApiKey) {
                                    cryptoBox.decrypt(rawValue)
                                } else {
                                    rawValue
                                }
                                Pair(sk, finalValue)
                            } else {
                                null
                            }
                        }
                        .toMap()
                )
            )
            setSettings(settings)
            Result.Success(settings)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN, e)
        }
    }

    override suspend fun exportSettings(settings: Settings, sink: Sink): Result<Unit, DataError.Local> = withContext(dispatcher) {
        try {
            val jsonMapper = Json {
                prettyPrint = true
            }
            val value = settings.toSettingsRepositoryEntity(cryptoBox)
            val json = jsonMapper.encodeToString(value)
            sink.use { writer ->
                writer.writeString(json)
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN, e)
        }
    }
}

private fun Settings.toSettingsRepositoryEntity(cryptoBox: CryptoBox): SettingsRepositoryEntity {
    val settingsEntity = SettingsRepositoryEntity(
        id = 0,
        language = get<Language>(SK.language)?.name ?: "EN",
        lastMaxImageSize = get<Int>(SK.maxImageSize)?.toLong() ?: 1200L,
        aisstreamApiKey = get<String>(SK.aisstreamApiKey)?.let { pw -> cryptoBox.encrypt(pw) } ?: "",
        location = get<String>(SK.location) ?: "",
        radiusOuter = get<String>(SK.radiusOuter) ?: "",
        radiusInner = get<String>(SK.radiusInner) ?: ""
    )
    return settingsEntity
}

@Serializable
private data class SettingsRepositoryEntity(
    val id: Long,
    val language: String,
    val lastMaxImageSize: Long,
    val aisstreamApiKey: EncryptedString,
    val location: String,
    val radiusOuter: String,
    val radiusInner: String
)
