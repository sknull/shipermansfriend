package de.visualdigits.shipermansfriend.domain.repository

import de.visualdigits.common.domain.model.errorhandling.Result
import de.visualdigits.shipermansfriend.domain.model.errorhandling.DataError
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import kotlinx.io.Sink

interface StarredVesselRepository {

    suspend fun upsertStarredVessel(entry: AisDataUi): Result<AisDataUi, DataError.Local>

    suspend fun deleteStarredVessel(mmsi: Long): Result<Unit, DataError.Local>

    suspend fun getAllStarredVessels(): Result<List<AisDataUi>, DataError.Local>

    suspend fun exportStarredVessels(fileName: String, sink: Sink): Result<Unit, DataError.Local>
}
