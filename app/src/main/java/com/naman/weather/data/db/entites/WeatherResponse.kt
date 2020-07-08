package com.naman.weather.data.db.entites

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherResponse(
    @Embedded(prefix = "clouds_")
    val clouds: Clouds,
    @Embedded(prefix = "coord_")
    val coord: Coord,
    val dt: Int,
    @PrimaryKey
    val id: Int,
    @Embedded(prefix = "main_")
    val main: Main,
    val name: String,
    @Embedded(prefix = "sys_")
    val sys: Sys,
    val visibility: Int,
//    @Embedded(prefix = "weather_")
//    val weather: List<Weather>,
    @Embedded(prefix = "wind_")
    val wind: Wind
)