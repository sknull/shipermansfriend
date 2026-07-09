package de.visualdigits.shipermansfriend.domain.repository

import de.visualdigits.common.domain.model.errorhandling.Result
import de.visualdigits.shipermansfriend.domain.model.errorhandling.DataError
import de.visualdigits.shipermansfriend.domain.model.starredvessels.StarredVessel
import kotlinx.io.Sink

interface StarredVesselRepository {

    suspend fun upsertStarredVessel(entry: StarredVessel): Result<StarredVessel, DataError.Local>

    suspend fun deleteStarredVessel(mmsi: Long): Result<Unit, DataError.Local>

    suspend fun getAllStarredVessels(): Result<List<StarredVessel>, DataError.Local>

    suspend fun exportStarredVessels(fileName: String, sink: Sink): Result<Unit, DataError.Local>
}
