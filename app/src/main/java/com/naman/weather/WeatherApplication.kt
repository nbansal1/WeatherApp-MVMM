package com.naman.weather

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.naman.weather.data.db.WeatherDatabase
import com.naman.weather.data.network.*
import com.naman.weather.data.repository.WeatherRepository
import com.naman.weather.data.repository.WeatherRepositoryImpl
import com.naman.weather.ui.TopCities.TopCitiesWeatherViewModelFactory
import com.naman.weather.ui.currentCity.CurrentCityForecastViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class WeatherApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidModule(this@WeatherApplication))

        bind() from singleton { WeatherDatabase(instance()) }
        bind() from singleton { instance<WeatherDatabase>().weatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { OpenWeatherAPIService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<WeatherRepository>() with singleton { WeatherRepositoryImpl(instance(),instance()) }
        bind() from provider { TopCitiesWeatherViewModelFactory(instance()) }
        bind() from provider { CurrentCityForecastViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}