package com.naman.weather.ui.currentCity

import androidx.lifecycle.ViewModel
import com.naman.weather.data.repository.WeatherRepository
import com.naman.weather.internal.lazyDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrentCityWeatherViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    var city: String = ""

    val cityForecast by lazyDeferred {
        weatherRepository.getCityForecast()
    }

    suspend fun getLatestDataForecast()  = GlobalScope.launch(Dispatchers.IO){
        weatherRepository.getLatestCityForecast(city)
    }
}
