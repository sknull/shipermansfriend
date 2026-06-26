package de.visualdigits.shipermansfriend.domain.repository

import de.visualdigits.common.domain.model.errorhandling.Result
import de.visualdigits.shipermansfriend.domain.model.errorhandling.DataError
import de.visualdigits.shipermansfriend.domain.model.geodata.MasterData
import kotlinx.io.Sink
import kotlinx.io.Source

interface MasterDataRepository {

    suspend fun getAllMasterData(): Result<List<MasterData>, DataError.Local>

    suspend fun getMasterData(mmsi: Long): Result<MasterData?, DataError.Local>

    suspend fun upsertMasterData(masterData: MasterData): Result<MasterData, DataError.Local>

    suspend fun importMasterData(source: Source): Result<Unit, DataError.Local>

    suspend fun exportMasterData(sink: Sink): Result<Unit, DataError.Local>
}
