package com.example.iec.ui.feature.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import com.example.iec.ui.feature.main.home.common.Location
import com.google.android.datatransport.runtime.scheduling.jobscheduling.SchedulerConfig.Flag
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


@SuppressLint("MissingPermission")
fun getLocation(context: Context) = callbackFlow<Location?> {
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationProviderClient.lastLocation
        .addOnSuccessListener { result ->
            Log.d("Location", result.latitude.toString())
            trySend(Location(result.latitude, result.longitude))
        }
        .addOnFailureListener {
            trySend(null)
        }
    awaitClose{
    }
}
fun checkLocationOn(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}


fun Location.getDistance(targetLocation: Location): Double{
    val R = 63710f // Radius of the earth in km
    val phi1 = this.lat * Math.PI / 180
    val phi2 = targetLocation.lat * Math.PI / 180

    val phiLat = targetLocation.lat - this.lat * Math.PI / 180
    val phiLon = (targetLocation.lng - this.lng) * Math.PI / 180
    val a = sin(phiLat / 2) * sin(phiLat / 2) +
            cos(phi1) * cos(phi2) *
            sin(phiLon / 2) * sin(phiLon / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return R * c
}
//suspend fun getLastLocation(): Location? {
//    return suspendCancellableCoroutine { continuation ->
//        // Check permissions
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            continuation.resume(null)
//            return@suspendCancellableCoroutine
//        }
//
//        fusedLocationClient.lastLocation
//            .addOnSuccessListener { location ->
//                continuation.resume(location)
//            }
//            .addOnFailureListener { exception ->
//                continuation.resumeWithException(exception)
//            }
//    }
//}