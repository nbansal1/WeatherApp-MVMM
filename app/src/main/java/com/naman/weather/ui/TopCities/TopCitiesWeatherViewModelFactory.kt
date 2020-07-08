package com.naman.weather.ui.TopCities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.naman.weather.data.repository.WeatherRepository

@Suppress("UNCHECKED_CAST")
class TopCitiesWeatherViewModelFactory(
    private val weatherRepository : WeatherRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TopCitiesWeatherViewModel(weatherRepository) as T
    }
}