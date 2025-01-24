package com.example.iec.ui.feature.main.home

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import com.example.iec.ui.feature.main.home.common.Location
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt


@SuppressLint("MissingPermission")
fun getLocation(context: Context) = callbackFlow<Location?> {
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationProviderClient.lastLocation
        .addOnSuccessListener { result ->
            if(result == null){
                requestLocation(context){
                    trySend(Location(it.latitude, it.longitude))
                }
            }else{
                trySend(Location(result.latitude, result.longitude))
            }
        }
        .addOnFailureListener {
            trySend(null)
        }
    awaitClose{
    }
}
@SuppressLint("MissingPermission")
fun requestLocation(context: Context, callback: (android.location.Location) -> Unit){
    val mLocationRequest: LocationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 60000).build()
    val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                if (location != null) {
                    callback(location)
                }
            }
        }
    }
    LocationServices.getFusedLocationProviderClient(context)
        .requestLocationUpdates(mLocationRequest, mLocationCallback, null)

}
fun checkLocationOn(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}


fun Location.getDistance(targetLocation: Location): Double{
    val R = 6371.0 // Earth's radius in kilometers

    // Convert latitude and longitude from degrees to radians
    val lat1 = Math.toRadians(this.lat)
    val lng1 = Math.toRadians(this.lng)
    val lat2 = Math.toRadians(targetLocation.lat)
    val lng2 = Math.toRadians(targetLocation.lng)

    // Difference in coordinates
    val deltaLat = lat2 - lat1
    val deltaLng = lng2 - lng1

    // Haversine formula
    val a = sin(deltaLat / 2).pow(2) + cos(lat1) * cos(lat2) * sin(deltaLng / 2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    // Calculate the distance
    val d = R * c
    return d
}

