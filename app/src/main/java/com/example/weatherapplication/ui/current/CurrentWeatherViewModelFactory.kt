package com.example.weatherapplication.ui.current


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.data.repository.WeatherRepository


class CurrentWeatherViewModelFactory(
    private val weatherRepository: WeatherRepository

) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(weatherRepository) as T
    }
}