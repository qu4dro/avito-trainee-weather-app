package dev.orlov.weather.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import com.google.android.gms.location.*

const val LOCATION_UPDATE_INTERVAL = 60000L
const val FASTEST_LOCATION_INTERVAL = LOCATION_UPDATE_INTERVAL / 4
const val REQUEST_CODE_LOCATION_PERMISSION = 0

class LocationLiveData(context: Context) : LiveData<Coordinates>() {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location.also {
                setLocationData(it)
            }
        }
        startLocationUpdates()
    }

    override fun onInactive() {
        super.onInactive()
        fusedLocationClient.removeLocationUpdates(locationCallBack)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallBack, null)
    }

    private val locationCallBack = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                setLocationData(location)
            }
        }

    }

    private fun setLocationData(location: Location?) {
        location ?: return
        value = Coordinates(location.longitude, location.latitude)
    }


    companion object {

        val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = LOCATION_UPDATE_INTERVAL
            fastestInterval = FASTEST_LOCATION_INTERVAL
            priority = Priority.PRIORITY_HIGH_ACCURACY
        }

    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallBack)
    }

}