package de.visualdigits.shipermansfriend.domain.repository

import de.visualdigits.common.domain.model.errorhandling.Result
import de.visualdigits.shipermansfriend.domain.model.errorhandling.DataError
import de.visualdigits.shipermansfriend.domain.model.photoprotocol.PhotoProtocolEntry
import kotlinx.io.Sink

interface PhotoProtocolRepository {

    suspend fun upsertPhotoProtocolEntryEntity(entry: PhotoProtocolEntry): Result<PhotoProtocolEntry, DataError.Local>

    suspend fun deletePhotoProtocolEntry(mmsi: Long): Result<Unit, DataError.Local>

    suspend fun getAllPhotoProtocolEntries(): Result<List<PhotoProtocolEntry>, DataError.Local>

    suspend fun exportPhotoProtocolEntries(fileName: String, sink: Sink): Result<Unit, DataError.Local>
}
