package com.example.weatherapplication.ui.current

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.example.weatherapplication.R
import com.example.weatherapplication.data.network.ConnectivityInterceptorImpl
import com.example.weatherapplication.data.network.OpenWeatherApiService
import com.example.weatherapplication.data.network.WeatherNetworkDataSourceImpl
import com.example.weatherapplication.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.internal.Internal.instance
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import android.os.Handler
import androidx.lifecycle.ViewModelProvider


class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()
    private lateinit var viewModel: CurrentWeatherViewModel

    companion object {
        fun newInstance() = CurrentWeatherFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

            viewModel = ViewModelProvider(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
            bindUI()



    }


    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(viewLifecycleOwner, Observer {

            if (it == null) return@Observer

            group_loading.visibility = View.GONE
            updateDateToToday()
            updateLocation(it.name)
            updateTemperatures(it.main.temp, it.main.feels_like)
            updatePressure(it.main.pressure)
            updateHumidity(it.main.humidity)
            updateWind(it.wind.deg, it.wind.speed)
            updateVisibility(it.visibility)
        })
    }


    private fun updateDateToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }
    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateTemperatures(temperature: Double, feelsLike: Double) {
        val tempUnit = ("°F")
        textView_temperature.text = "$temperature$tempUnit"
        textView_feels_like_temperature.text = "Feels like $feelsLike$tempUnit"
    }

    private fun updatePressure(pressure: Double) {
        val pressureUnit = ("Pa")
        textView_pressure.text = "Pressure: $pressure$pressureUnit"
    }

    private fun updateHumidity(humidity: Double) {
        val humidityUnit = ("g.m-3")
        textView_humidity.text = "Humidity: $humidity$humidityUnit"
    }

    private fun updateWind(windDirection: Double, windSpeed: Double) {
        val windUnit = ("kph")
        val windDirectionUnit = ("°")
        textView_wind.text = "Wind: $windDirection $windDirectionUnit, $windSpeed $windUnit"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        val visibilityUnit = ("km")
        textView_visibility.text = "Visibility: $visibilityDistance $visibilityUnit"
    }

}
