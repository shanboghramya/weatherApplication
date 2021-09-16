package com.example.weatherapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapplication.data.network.response.CurrentWeatherResponse

@Database(
    entities = [CurrentWeatherResponse::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun currentWeatherDao(): CurrentWeatherDao

    companion object {
        @Volatile private var instance: WeatherDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                WeatherDatabase::class.java, "data.db")
                .build()
    }


}