package com.naman.weather.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.naman.weather.data.network.response.CitiesWeatherResponse
import com.naman.weather.data.db.entites.WeatherResponse
import com.naman.weather.data.network.response.forecast.CityForecastResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "2247c72534c579476337f02a4b672f2b";
//https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=439d4b804bc8187953eb36d2a8c26a02
//https://api.openweathermap.org/data/2.5/group?id=524901&units=metric&appid=439d4b804bc8187953eb36d2a8c26a02

//http://api.openweathermap.org/data/2.5/forecast?q=London&units=1&APPID=2247c72534c579476337f02a4b672f2b&cnt=1

interface OpenWeatherAPIService {

    @GET("forecast")
    fun getCityForecast(
        @Query("q") cityName:String = "London",
        @Query("cnt") count : Int = 5,
        @Query("units") units: String = "metric"
    ) : Deferred<CityForecastResponse>

    @GET("group")
    fun getTopCities(
        @Query("id") ids : String = "1273294,524901,703448,2643743,5128581,1277333,1168579,5391811,4930956,2147714,5506956,4164138,2988507,292223,1880252,3128760,4219762,3117735,292968,2759794",
        @Query("units") units: String = "metric"
    ) : Deferred<CitiesWeatherResponse>

    companion object{
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ) : OpenWeatherAPIService {
            val requestInterceptor = Interceptor{chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("appid",
                        API_KEY
                    )
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherAPIService::class.java)
        }
    }
}