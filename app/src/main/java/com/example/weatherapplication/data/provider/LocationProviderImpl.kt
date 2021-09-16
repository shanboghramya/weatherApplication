package com.example.weatherapplication.data.provider

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.example.weatherapplication.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.example.weatherapplication.data.network.response.CurrentWeatherResponse
import com.example.weatherapplication.internal.LocationPermissionNotGrantedException
import com.example.weatherapplication.internal.asDeferred
import kotlinx.android.synthetic.main.current_weather_fragment.view.*
import kotlinx.coroutines.Deferred

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"

class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context) : PreferenceProvider(context),LocationProvider {

    private val appContext = context.applicationContext

    override suspend fun hasLocationChanged(currentWeatherResponse: CurrentWeatherResponse): Boolean {
        val deviceLocationChanged = try {
            hasDeviceLocationChanged(currentWeatherResponse)
        } catch (e: LocationPermissionNotGrantedException) {
            false
        }
        return deviceLocationChanged || hasCustomLocationChanged(currentWeatherResponse)
    }

    @SuppressLint("ResourceType")
    override suspend fun getPreferredLocationString(): String {
//        if (isUsingDeviceLocation()) {
//            try {
//                val deviceLocation = getLastDeviceLocation().await()
//                    ?: return "${getCustomLocationName()}"
//                return "${deviceLocation.latitude},${deviceLocation.longitude}"
//            } catch (e: LocationPermissionNotGrantedException) {
//                return "${getCustomLocationName()}"
//            }
//        }
//        else
//            return "${getCustomLocationName()}"

        return appContext.getString(R.id.edit_text_location)

    }


    private suspend fun hasDeviceLocationChanged(currentWeatherResponse: CurrentWeatherResponse): Boolean {
        if (!isUsingDeviceLocation())
            return false

        val deviceLocation = getLastDeviceLocation().await()
            ?: return false

        // Comparing doubles cannot be done with "=="
        val comparisonThreshold = 0.03

        return Math.abs(deviceLocation.latitude - currentWeatherResponse.coord.lat) > comparisonThreshold &&
                Math.abs(deviceLocation.longitude - currentWeatherResponse.coord.lon) > comparisonThreshold
    }

    private fun hasCustomLocationChanged(currentWeatherResponse: CurrentWeatherResponse): Boolean {
        if (!isUsingDeviceLocation()) {
            val customLocationName = getCustomLocationName()
            return customLocationName != currentWeatherResponse.name
        }
        return false
    }

    private fun getCustomLocationName(): String? {
        return preferences.getString(CUSTOM_LOCATION, null)
    }


    private fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(USE_DEVICE_LOCATION, true)
    }

    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocation(): Deferred<Location?> {
        return if (hasLocationPermission())
            fusedLocationProviderClient.lastLocation.asDeferred()
        else
            throw LocationPermissionNotGrantedException()
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(appContext,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

}