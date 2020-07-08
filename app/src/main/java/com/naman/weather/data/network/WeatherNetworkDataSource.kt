package com.naman.weather.data.network

import androidx.lifecycle.LiveData
import com.naman.weather.data.network.response.CitiesWeatherResponse
import com.naman.weather.data.network.response.forecast.CityForecastResponse

interface WeatherNetworkDataSource {
    val downloadedCitiesWeather : LiveData<CitiesWeatherResponse>

    suspend fun fetchCitiesWeather()

    val downloadedCityForecast : LiveData<CityForecastResponse>

    suspend fun fetchCityForecast(city:String)

}