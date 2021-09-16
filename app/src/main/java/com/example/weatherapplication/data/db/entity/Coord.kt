package com.example.weatherapplication.data.db.entity

import com.google.gson.annotations.SerializedName

data class Coord (
    @SerializedName("lon") val lon : Double,
    @SerializedName("lat") val lat : Double
)