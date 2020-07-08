package com.naman.weather.data.network.response

import com.naman.weather.data.db.entites.WeatherResponse

data class CitiesWeatherResponse(val cnt: Int,val list: List<WeatherResponse>)