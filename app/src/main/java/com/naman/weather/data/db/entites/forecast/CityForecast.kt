package com.naman.weather.data.db.entites.forecast


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "forecast")
data class CityForecast(
    @Embedded(prefix = "clouds_")
    @SerializedName("clouds")
    val clouds: Clouds,
    @PrimaryKey
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("dt_txt")
    val dtTxt: String,
    @Embedded(prefix = "main_")
    @SerializedName("main")
    val main: Main,
    @Embedded(prefix = "sys_")
    @SerializedName("sys")
    val sys: Sys,
    @Embedded(prefix = "wind_")
    @SerializedName("wind")
    val wind: Wind
)