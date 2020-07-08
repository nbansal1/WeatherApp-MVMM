package com.naman.weather.ui.currentCity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.naman.weather.data.repository.WeatherRepository

@Suppress("UNCHECKED_CAST")
class CurrentCityForecastViewModelFactory  (
    private val weatherRepository : WeatherRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentCityWeatherViewModel(weatherRepository) as T
    }
}