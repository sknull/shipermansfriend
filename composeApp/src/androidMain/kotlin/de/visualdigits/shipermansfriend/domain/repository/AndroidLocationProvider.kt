package de.visualdigits.shipermansfriend.domain.repository

import android.annotation.SuppressLint
import android.content.Context
import co.touchlab.kermit.Severity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import de.visualdigits.common.domain.model.errorhandling.LogMessage.Companion.log
import de.visualdigits.common.domain.model.geodata.Location
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import android.os.Looper

class AndroidLocationProvider(private val context: Context) : LocationProvider {

    @SuppressLint("MissingPermission")
    override fun observeLocation(): Flow<Location> = channelFlow<Location> {
        val locationClient = LocationServices.getFusedLocationProviderClient(context)

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            10000
        ).setMinUpdateDistanceMeters(20f).build()

        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { androidLoc ->
                    trySendBlocking(Location(androidLoc.latitude, androidLoc.longitude))
                }
            }
        }

        try {
            // ZENTRALE KORREKTUR: Looper.getMainLooper() statt null übergeben!
            // Das zwingt Android 11, den GPS-Callback sauber auf dem Hauptthread anzustupsen.
            locationClient.requestLocationUpdates(locationRequest, callback, Looper.getMainLooper())
        } catch (e: Exception) {
            log(Severity.Warn, "Could not request location updates: ${e.message}", withTag = "AIS")
        }

        // WICHTIG: awaitClose MUSS ganz unten außerhalb des try-Blocks stehen!
        awaitClose {
            try {
                locationClient.removeLocationUpdates(callback)
            } catch (_: Exception) {}
        }
    }
}
