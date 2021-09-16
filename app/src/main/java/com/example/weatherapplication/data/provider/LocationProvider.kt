package com.example.weatherapplication.data.provider

import com.example.weatherapplication.data.network.response.CurrentWeatherResponse

interface LocationProvider {
    suspend fun hasLocationChanged(currentWeatherResponse: CurrentWeatherResponse): Boolean
    suspend fun getPreferredLocationString(): String
}