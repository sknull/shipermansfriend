package de.visualdigits.shipermansfriend.data.repository

import de.visualdigits.common.domain.model.errorhandling.Result
import de.visualdigits.shipermansfriend.ShipermansFriendDatabaseQueries
import de.visualdigits.shipermansfriend.data.database.toMasterData
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType
import de.visualdigits.shipermansfriend.domain.model.errorhandling.DataError
import de.visualdigits.shipermansfriend.domain.model.geodata.MasterData
import de.visualdigits.shipermansfriend.domain.repository.MasterDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.Sink
import kotlinx.io.Source
import kotlinx.io.readString
import kotlinx.io.writeString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class DefaultMasterDataRepository(
    val dao: ShipermansFriendDatabaseQueries
) : MasterDataRepository {

    override suspend fun getAllMasterData(): Result<List<MasterData>, DataError.Local> = withContext(Dispatchers.IO) {
        try {
            Result.Success(dao.getAllMasterData().executeAsList().map { mde -> mde.toMasterData() })
        } catch (e: Exception) {
            Result.Error(DataError.Local.SERIALIZATION, e)
        }
    }

    override suspend fun getMasterData(mmsi: Long): Result<MasterData?, DataError.Local> = withContext(Dispatchers.IO) {
        try {
            Result.Success(dao.getMasterDataByMmsi(mmsi).executeAsOneOrNull()?.toMasterData())
        } catch (e: Exception) {
            Result.Error(DataError.Local.SERIALIZATION, e)
        }
    }

    override suspend fun upsertMasterData(masterData: MasterData): Result<MasterData, DataError.Local> = withContext(Dispatchers.IO) {
        try {
            dao.upsertMasterData(
                messageType = masterData.messageType.name,
                name = masterData.name,
                mmsi = masterData.mmsi,
                timeUtc = masterData.timeUtc.toString(),
                imoNumber = masterData.imoNumber,
                callSign = masterData.callSign,
                destination = masterData.destination,
                totalLength = masterData.totalLength,
                totalWidth = masterData.totalWidth,
                shipType = masterData.shipType.code,
                maximumStaticDraught = masterData.maximumStaticDraught,
            )
            Result.Success(dao.getMasterDataByMmsi(masterData.mmsi).executeAsOne().toMasterData())
        } catch (e: Exception) {
            Result.Error(DataError.Local.SERIALIZATION, e)
        }
    }

    override suspend fun exportMasterData(sink: Sink): Result<Unit, DataError.Local> = withContext(Dispatchers.IO) {
        try {
            val jsonMapper = Json {
                prettyPrint = true
            }
            val masterData = dao.getAllMasterData()
                .executeAsList()
                .sortedBy { md -> md.name.lowercase() }
                .map { mde ->
                    MasterDataDatabaseEntity(
                        messageType = mde.messageType,
                        name = mde.name.trim(),
                        mmsi = mde.mmsi,
                        timeUtc = mde.timeUtc,
                        imoNumber = mde.imoNumber,
                        callSign = mde.callSign.trim(),
                        destination = mde.destination.trim(),
                        totalLength = mde.totalLength,
                        totalWidth = mde.totalWidth,
                        shipType = mde.shipType,
                        maximumStaticDraught = mde.maximumStaticDraught,
                    )
                }
            val json = jsonMapper.encodeToString(masterData)
            sink.use { writer ->
                writer.writeString(json)
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.SERIALIZATION, e)
        }
    }

    override suspend fun importMasterData(source: Source): Result<Unit, DataError.Local> = withContext(Dispatchers.IO) {
        try {
            val jsonMapper = Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            }
            val json = source.use { ins ->
                ins.readString()
            }
            jsonMapper
                .decodeFromString<List<MasterDataDatabaseEntity>>(json)
                .filter { mde -> mde.mmsi != 0L }
                .forEach { mde ->
                    dao.upsertMasterData(
                        messageType = mde.messageType,
                        name = mde.name,
                        mmsi = mde.mmsi,
                        timeUtc = mde.timeUtc,
                        imoNumber = mde.imoNumber,
                        callSign = mde.callSign,
                        destination = mde.destination,
                        totalLength = mde.totalLength,
                        totalWidth = mde.totalWidth,
                        shipType = mde.shipType,
                        maximumStaticDraught = mde.maximumStaticDraught,
                    )
                }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.SERIALIZATION, e)
        }
    }
}

@Serializable
data class MasterDataDatabaseEntity(
    @SerialName("messageType") val messageType: String = MessageType.UnknownMessage.name,
    @SerialName("name") val name: String,
    @SerialName("mmsi") val mmsi: Long,
    @SerialName("timeUtc") val timeUtc: String,
    @SerialName("imoNumber") val imoNumber: Long,
    @SerialName("callSign") val callSign: String,
    @SerialName("destination") val destination: String,
    @SerialName("totalLength") val totalLength: Long,
    @SerialName("totalWidth") val totalWidth: Long,
    @SerialName("shipType") val shipType: Long,
    @SerialName("maximumStaticDraught") val maximumStaticDraught: Double,
)
