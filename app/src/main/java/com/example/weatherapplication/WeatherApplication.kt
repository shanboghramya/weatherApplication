package com.example.weatherapplication

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDexApplication
import androidx.preference.PreferenceManager
import com.example.weatherapplication.data.db.WeatherDatabase
import com.example.weatherapplication.data.network.*
import com.example.weatherapplication.data.provider.LocationProvider
import com.example.weatherapplication.data.provider.LocationProviderImpl
import com.example.weatherapplication.data.repository.WeatherRepository
import com.example.weatherapplication.data.repository.WeatherRepositoryImpl
import com.example.weatherapplication.ui.current.CurrentWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class WeatherApplication : MultiDexApplication(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@WeatherApplication))

        bind() from singleton { WeatherDatabase(instance()) }
        bind() from singleton { instance<WeatherDatabase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { OpenWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<WeatherRepository>() with singleton { WeatherRepositoryImpl(instance(), instance(),instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(),instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }

    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}