package de.visualdigits.shipermansfriend.domain.repository

import de.visualdigits.common.domain.model.geodata.Location
import kotlinx.coroutines.flow.Flow

interface LocationProvider {

    /** Returns the current location or null if GPS is disabled/permission denied */
    fun observeLocation(): Flow<Location>
}
