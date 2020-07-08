package com.naman.weather.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.naman.weather.data.network.response.CitiesWeatherResponse
import com.naman.weather.data.network.response.forecast.CityForecastResponse
import com.naman.weather.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val openWeatherAPIService: OpenWeatherAPIService
) : WeatherNetworkDataSource {

    private val _downloadCitiesWeather = MutableLiveData<CitiesWeatherResponse>()
    private val _downloadCityForecast = MutableLiveData<CityForecastResponse>()

    override val downloadedCitiesWeather: LiveData<CitiesWeatherResponse>
        get() = _downloadCitiesWeather

    override val downloadedCityForecast: LiveData<CityForecastResponse>
        get() = _downloadCityForecast

    override suspend fun fetchCitiesWeather() {
        try {
            val fetchedCitiesWeather  = openWeatherAPIService.getTopCities().await()
//            val cityForecast = openWeatherAPIService.getCityForecast().await();
//            cityForecast.list.map {
//                Log.v("Forecast", "${it.toString()}")
//            }
            _downloadCitiesWeather.postValue(fetchedCitiesWeather)
        }catch (e: NoConnectivityException){
            Log.e("Connectivity", " No internet connection", e)

        }
    }

    override suspend fun fetchCityForecast(city:String) {
        try {
            val  fetchedCityForecast = openWeatherAPIService.getCityForecast(city).await()
            _downloadCityForecast.postValue(fetchedCityForecast)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection" , e)
        }
    }
}