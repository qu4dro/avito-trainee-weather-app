package dev.orlov.weather.utils

import android.Manifest
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions
import java.util.*

data class Coordinates(val lng: Double, val lat: Double)

object LocationUtility {

    fun hasLocationPermissions(context: Context) =
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
                //place for code for background location
            )
        }


    fun getCityString(lat: Double, lon: Double, context: Context): String {
        val addresses: List<Address>
        val geocoder: Geocoder = Geocoder(context, Locale.getDefault())

        addresses = geocoder.getFromLocation(
            lat,
            lon,
            1
        )
        val city: String =
            addresses[0].locality
        return city
    }
}