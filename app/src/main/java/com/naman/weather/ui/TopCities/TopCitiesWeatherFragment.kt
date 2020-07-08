package com.naman.weather.ui.TopCities

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.naman.weather.R
import com.naman.weather.data.network.ConnectivityInterceptorImpl
import com.naman.weather.data.network.OpenWeatherAPIService
import com.naman.weather.data.network.WeatherNetworkDataSourceImpl
import com.naman.weather.ui.CitiesWeatherAdapter
import com.naman.weather.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.top_cities_weather_fragment.*
import kotlinx.android.synthetic.main.top_cities_weather_fragment.swipeRefresh
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class TopCitiesWeatherFragment : ScopedFragment() , KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: TopCitiesWeatherViewModelFactory by instance()

    private lateinit var viewModel: TopCitiesWeatherViewModel
    private lateinit var weatherAdapter: CitiesWeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.top_cities_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(TopCitiesWeatherViewModel::class.java)
        setAdapter();
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Top Cities"
        bindUI()
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        swipeRefresh.setOnRefreshListener {
            bindFetchedUI()
        }
    }

    private fun bindFetchedUI() = launch {
        viewModel.latestFetchedCitiesWeather.await()
        swipeRefresh.isRefreshing = false
    }

    private fun setAdapter() {
        weatherAdapter = CitiesWeatherAdapter(activity?.baseContext, listOf())
        recycler_view.layoutManager = LinearLayoutManager(activity?.baseContext)
        recycler_view.adapter = weatherAdapter;
    }

    private fun bindUI() = launch{
        val citiesWeather = viewModel.citiesWeather.await()
        citiesWeather.observe(this@TopCitiesWeatherFragment, Observer {
            if(it.isEmpty()) {
                swipeRefresh.isRefreshing = true
                bindFetchedUI()
            }
            weatherAdapter.citiesWeatherList = it
            weatherAdapter.notifyDataSetChanged()
        })
    }
}
