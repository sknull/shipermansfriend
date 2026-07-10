package de.visualdigits.shipermansfriend.data.repository

import co.touchlab.kermit.Logger
import de.visualdigits.common.domain.model.errorhandling.Result
import de.visualdigits.shipermansfriend.ShipermansFriendDatabaseQueries
import de.visualdigits.shipermansfriend.data.database.toAisDataUi
import de.visualdigits.shipermansfriend.data.database.toStarredVesselEntity
import de.visualdigits.shipermansfriend.data.database.upsertStarredVesselEntity
import de.visualdigits.shipermansfriend.domain.model.errorhandling.DataError
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.repository.StarredVesselRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.Sink
import kotlinx.io.writeString

class DefaultStarredVesselRepository(
    val dao: ShipermansFriendDatabaseQueries
) : StarredVesselRepository {

    override suspend fun upsertStarredVessel(entry: AisDataUi): Result<AisDataUi, DataError.Local> = withContext(Dispatchers.IO) {
        try {
            dao.upsertStarredVesselEntity(entry.toStarredVesselEntity())
            Result.Success(dao.getStarredVesselEntityByMmsi(entry.mmsi).executeAsOne().toAisDataUi())
        } catch (e: Exception) {
            Result.Error(DataError.Local.SERIALIZATION, e)
        }
    }

    override suspend fun deleteStarredVessel(mmsi: Long): Result<Unit, DataError.Local> = withContext(Dispatchers.IO) {
        try {
            dao.deleteStarredVesselEntity(mmsi)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.SERIALIZATION, e)
        }
    }

    override suspend fun getAllStarredVessels(): Result<List<AisDataUi>, DataError.Local> = withContext(Dispatchers.IO) {
        try {
            Result.Success(dao.getAllStarredVesselEntities().executeAsList().map { it.toAisDataUi() })
        } catch (e: Exception) {
            Result.Error(DataError.Local.SERIALIZATION, e)
        }
    }

    override suspend fun exportStarredVessels(fileName: String, sink: Sink): Result<Unit, DataError.Local> = withContext(Dispatchers.IO) {
        try {
            Logger.i("Exporting starred vessels")
            val rows = dao.getAllStarredVesselEntities()
                .executeAsList()
                .map { v -> v.toAisDataUi() }
                .sortedBy { v -> v.timeUtc }
                .joinToString("\n") { v -> v.toCsv() }
            val csv = "${AisDataUi.csvTitleRow()}\n$rows"
            if (fileName.endsWith(".csv", ignoreCase = true)) {
                sink.use { writer ->
                    writer.writeString(csv)
                }
                dao.deleteAllStarredVesselEntities()
                Logger.i("Export was successful")
                Result.Success(Unit)
            } else {
                Logger.e("Unsupported file type: ${fileName.substringAfterLast(".")}")
                Result.Error(DataError.Local.SERIALIZATION)
            }

        } catch (e: Exception) {
            Logger.e("Could not export starred vessels", e)
            Result.Error(DataError.Local.SERIALIZATION, e)
        }
    }
}
