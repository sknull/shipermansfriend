package de.visualdigits.shipermansfriend.domain.repository

import de.visualdigits.common.domain.model.geodata.Location
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.hours

class JvmLocationProvider : LocationProvider {

    override fun observeLocation(): Flow<Location> = flow {
        val defaultLocation = Location(
            latitude = 53.545977,
            longitude = 9.9680454
        )
        emit(defaultLocation)

        while (true) {
            delay(1.hours)
        }
    }
}
