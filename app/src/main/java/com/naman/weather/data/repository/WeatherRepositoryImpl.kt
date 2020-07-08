package com.naman.weather.data.repository

import androidx.lifecycle.LiveData
import com.naman.weather.data.db.WeatherDao
import com.naman.weather.data.db.entites.WeatherResponse
import com.naman.weather.data.db.entites.forecast.CityForecast
import com.naman.weather.data.network.WeatherNetworkDataSource
import com.naman.weather.data.network.response.CitiesWeatherResponse
import com.naman.weather.data.network.response.forecast.CityForecastResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class WeatherRepositoryImpl(
    private val weatherDao: WeatherDao,
    private val networkDataSource: WeatherNetworkDataSource
) : WeatherRepository {

    init {
        networkDataSource.downloadedCitiesWeather.observeForever {
            persistFetchedCitiesWeather(it)
        }

        networkDataSource.downloadedCityForecast.observeForever {
            persistFetchedCityForecast(it)
        }

    }
    override suspend fun getCitiesWeather(): LiveData<List<WeatherResponse>> {
        return withContext(Dispatchers.IO){
//            initWeatherData()
            weatherDao.getCitiesWeather()
        }
    }

    override suspend fun getLatestFetchCitiesWeather(){
        return withContext(Dispatchers.IO){
            initWeatherData()
        }
    }

    override suspend fun getCityForecast(): LiveData<List<CityForecast>> {
        return withContext(Dispatchers.IO){
//            initCityForecastData(city)
            weatherDao.getCityForecast()
        }
    }

    override suspend fun getLatestCityForecast(city: String){
        return withContext(Dispatchers.IO){
            initCityForecastData(city)
        }
    }

    private suspend fun initCityForecastData(city: String) {
//        if(isFetchedCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCityForecast(city)
    }

    private fun persistFetchedCitiesWeather(citiesWeatherResponse: CitiesWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            weatherDao.upsert(citiesWeatherResponse.list)
        }
    }

    private fun persistFetchedCityForecast(cityForecastResponse: CityForecastResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            weatherDao.upsertForecast(cityForecastResponse.list)
        }
    }

    private suspend fun initWeatherData(){
        if(isFetchedCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather(){
        networkDataSource.fetchCitiesWeather()
    }

    private suspend fun fetchCityForecast(city: String){
        networkDataSource.fetchCityForecast(city)
    }

    private fun isFetchedCurrentNeeded(lastFetchTime: ZonedDateTime) : Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}