package com.naman.weather.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.naman.weather.data.db.entites.WeatherResponse
import com.naman.weather.data.db.entites.forecast.CityForecast

@Database(entities = [WeatherResponse::class, CityForecast::class],
    version = 1)
abstract class WeatherDatabase  : RoomDatabase(){
    abstract fun weatherDao() : WeatherDao

    companion object{
        @Volatile
        private var instance: WeatherDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context : Context) = instance ?:
                synchronized(LOCK) {
                    instance ?: buildDatabase(context).also { instance = it }
                }
        private fun  buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                WeatherDatabase::class.java, "weather.db")
                .build()
    }
}