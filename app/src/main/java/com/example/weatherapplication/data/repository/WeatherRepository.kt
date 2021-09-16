package com.example.weatherapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherapplication.data.network.response.CurrentWeatherResponse


interface WeatherRepository {

    suspend fun getCurrentWeather(): LiveData<out CurrentWeatherResponse>
}