
package com.example.weatherapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapplication.data.network.response.CURRENT_WEATHER_ID
import com.example.weatherapplication.data.network.response.CurrentWeatherResponse

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherResponse)

    @Query("select * from current_weather where tid = $CURRENT_WEATHER_ID")
    fun getWeatherResponse(): LiveData<CurrentWeatherResponse>

}