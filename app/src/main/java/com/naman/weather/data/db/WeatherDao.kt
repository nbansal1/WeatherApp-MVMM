package com.naman.weather.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.naman.weather.data.db.entites.WeatherResponse
import com.naman.weather.data.db.entites.forecast.CityForecast

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherResponseList: List<WeatherResponse>)

    @Query("select * from weather")
    fun getCitiesWeather() : LiveData<List<WeatherResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertForecast(forecastList: List<CityForecast>)

    @Query("select * from forecast")
    fun getCityForecast() : LiveData<List<CityForecast>>

}