package com.naman.weather.data.network.response.forecast


import com.google.gson.annotations.SerializedName
import com.naman.weather.data.db.entites.forecast.City
import com.naman.weather.data.db.entites.forecast.CityForecast

data class CityForecastResponse(
    @SerializedName("city")
    val city: City,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: List<CityForecast>,
    @SerializedName("message")
    val message: Int
)