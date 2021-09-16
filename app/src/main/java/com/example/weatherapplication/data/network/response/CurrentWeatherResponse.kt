package com.example.weatherapplication.data.network.response

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapplication.data.db.entity.*

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherResponse(
    val base: String,
    @Embedded(prefix = "clouds_")
    val clouds: Clouds,
    val cod: Int,
    @Embedded(prefix = "coord_")
    val coord: Coord,
    val dt: Int,
    val id: Int,
    @Embedded(prefix = "main_")
    val main: Main,
    val name: String,
    @Embedded(prefix = "sys_")
    val sys: Sys,
    val timezone: Int,
    val visibility: Double,
    /*@Embedded(prefix = "weather_")
    val weather: List<Weather>,*/
    @Embedded(prefix = "wind_")
    val wind: Wind
){
    @PrimaryKey(autoGenerate = false)
    var tid: Int = CURRENT_WEATHER_ID

}