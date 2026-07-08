package de.visualdigits.shipermansfriend.data.repository

import co.touchlab.kermit.Logger
import de.visualdigits.common.domain.model.errorhandling.Result
import de.visualdigits.shipermansfriend.ShipermansFriendDatabaseQueries
import de.visualdigits.shipermansfriend.data.database.toPhotoProtocolEntry
import de.visualdigits.shipermansfriend.data.database.toPhotoProtocolEntryEntity
import de.visualdigits.shipermansfriend.data.database.upsertPhotoProtocolEntryEntity
import de.visualdigits.shipermansfriend.domain.model.errorhandling.DataError
import de.visualdigits.shipermansfriend.domain.model.photoprotocol.PhotoProtocolEntry
import de.visualdigits.shipermansfriend.domain.repository.PhotoProtocolRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.Sink
import kotlinx.io.writeString

class DefaultPhotoProtocolRepository(
    val dao: ShipermansFriendDatabaseQueries
) : PhotoProtocolRepository {

    override suspend fun upsertPhotoProtocolEntryEntity(entry: PhotoProtocolEntry): Result<PhotoProtocolEntry, DataError.Local> = withContext(Dispatchers.IO) {
        try {
            dao.upsertPhotoProtocolEntryEntity(entry.toPhotoProtocolEntryEntity())
            Result.Success(dao.getPhotoProtocolEntryEntityByMmsi(entry.mmsi).executeAsOne().toPhotoProtocolEntry())
        } catch (e: Exception) {
            Result.Error(DataError.Local.SERIALIZATION, e)
        }
    }

    override suspend fun deletePhotoProtocolEntry(mmsi: Long): Result<Unit, DataError.Local> = withContext(Dispatchers.IO) {
        try {
            dao.deletePhotoProtocolEntryEntity(mmsi)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.SERIALIZATION, e)
        }
    }

    override suspend fun getAllPhotoProtocolEntries(): Result<List<PhotoProtocolEntry>, DataError.Local> = withContext(Dispatchers.IO) {
        try {
            Result.Success(dao.getAllPhotoProtocolEntryEntities().executeAsList().map { pe -> pe.toPhotoProtocolEntry() })
        } catch (e: Exception) {
            Result.Error(DataError.Local.SERIALIZATION, e)
        }
    }

    override suspend fun exportPhotoProtocolEntries(fileName: String, sink: Sink): Result<Unit, DataError.Local> = withContext(Dispatchers.IO) {
        try {
            Logger.i("Exporting photo protocol")
            val rows = dao.getAllPhotoProtocolEntryEntities()
                .executeAsList()
                .sortedBy { v -> v.timeUtc }
                .joinToString("\n") { v -> v.toPhotoProtocolEntry().toCsvRow() }
            val csv = "${PhotoProtocolEntry.csvTitleRow()}\n$rows"
            if (fileName.endsWith(".csv", ignoreCase = true)) {
                sink.use { writer ->
                    writer.writeString(csv)
                }
                dao.deleteAllPhotoProtocolEntryEntities()
                Logger.i("Export was successful")
                Result.Success(Unit)
            } else {
                Logger.e("Unsupported file type: ${fileName.substringAfterLast(".")}")
                Result.Error(DataError.Local.SERIALIZATION)
            }

        } catch (e: Exception) {
            Logger.e("Could not export photo protocol", e)
            Result.Error(DataError.Local.SERIALIZATION, e)
        }
    }
}
