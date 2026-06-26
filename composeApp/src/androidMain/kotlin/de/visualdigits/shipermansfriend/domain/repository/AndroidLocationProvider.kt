package de.visualdigits.shipermansfriend.domain.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location as AndroidLocation
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.errorhandling.LogMessage.Companion.log
import de.visualdigits.common.domain.model.geodata.Location
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

class AndroidLocationProvider(private val context: Context) : LocationProvider {

    @SuppressLint("MissingPermission")
    override fun observeLocation(): Flow<Location> = channelFlow<Location> {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val listener = object : LocationListener {
            override fun onLocationChanged(androidLoc: AndroidLocation) {
                trySendBlocking(Location(androidLoc.latitude, androidLoc.longitude))
            }
            @Deprecated("Deprecated in API 29")
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        // ZENTRALE KORREKTUR: Den gesamten Hardware-Zugriff mit try/catch absichern!
        try {
            // 1. Fetch cached location instantly from native providers
            val lastGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val lastNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            val initialLoc = lastGps ?: lastNetwork

            initialLoc?.let {
                trySendBlocking(Location(it.latitude, it.longitude))
            }

            // 2. Request native live updates
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                10000L,
                0f,
                listener,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            // If runtime permissions are not yet granted by the user, we catch the exception safely
            log(Severity.Warn, "Location permissions missing or not yet granted: ${e.message}", withTag = "AIS")
        } catch (e: Exception) {
            log(Severity.Error, "Native location access failed: ${e.message}", e, withTag = "AIS")
        }

        awaitClose {
            try {
                locationManager.removeUpdates(listener)
            } catch (_: Exception) {}
        }
    }
}
