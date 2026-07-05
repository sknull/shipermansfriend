package de.visualdigits.shipermansfriend.data.repository

import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.errorhandling.LogMessage.Companion.log
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
            log(Severity.Info, "Exporting photo protocol", withTag = "AIS")
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
                log(Severity.Info, "Export was successful", withTag = "AIS")
                Result.Success(Unit)
            } else {
                log(Severity.Error, "Unsupported file type: ${fileName.substringAfterLast(".")}", withTag = "AIS")
                Result.Error(DataError.Local.SERIALIZATION)
            }

        } catch (e: Exception) {
            log(Severity.Error, "Could not export photo protocol", e, withTag = "AIS")
            Result.Error(DataError.Local.SERIALIZATION, e)
        }
    }
}
