package com.example.weatherapplication.ui.current


import androidx.lifecycle.ViewModel
import com.example.weatherapplication.data.repository.WeatherRepository
import com.example.weatherapplication.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val weather by lazyDeferred(){
        weatherRepository.getCurrentWeather()
    }

}
