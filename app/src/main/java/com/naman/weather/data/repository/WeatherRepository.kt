package com.naman.weather.data.repository

import androidx.lifecycle.LiveData
import com.naman.weather.data.db.entites.WeatherResponse
import com.naman.weather.data.db.entites.forecast.CityForecast

interface WeatherRepository {
    suspend fun getLatestFetchCitiesWeather()
    suspend fun getCitiesWeather() : LiveData<List<WeatherResponse>>
    suspend fun getLatestCityForecast(city: String)
    suspend fun getCityForecast() : LiveData<List<CityForecast>>
}