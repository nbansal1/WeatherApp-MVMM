package com.naman.weather.ui.TopCities

import androidx.lifecycle.ViewModel
import com.naman.weather.data.repository.WeatherRepository
import com.naman.weather.internal.lazyDeferred

class TopCitiesWeatherViewModel(
    private val weatherRepository:WeatherRepository): ViewModel() {
    val citiesWeather by lazyDeferred{
        weatherRepository.getCitiesWeather()
    }

    val latestFetchedCitiesWeather by lazyDeferred{
        weatherRepository.getLatestFetchCitiesWeather()
    }
}
