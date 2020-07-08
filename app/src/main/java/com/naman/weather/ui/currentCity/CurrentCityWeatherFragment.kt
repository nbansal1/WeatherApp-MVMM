package com.naman.weather.ui.currentCity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.naman.weather.R
import com.naman.weather.ui.CityForecastAdapter
import com.naman.weather.ui.CustomDropDownAdapter
import com.naman.weather.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.top_cities_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.io.IOException
import java.util.*

class CurrentCityWeatherFragment : ScopedFragment(), KodeinAware {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override val kodein by closestKodein()

    private val viewModelFactory: CurrentCityForecastViewModelFactory by instance()

    private lateinit var viewModel: CurrentCityWeatherViewModel
    private lateinit var cityForecastAdapter: CityForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.curent_city_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this,viewModelFactory).get(CurrentCityWeatherViewModel::class.java)

        setAdapter()
        bindUI()
        setSpinnerAdapter()

//        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
//        swipeRefresh.setOnRefreshListener {
//
//        }
    }

    private fun setSpinnerAdapter() {
        val listItemsTxt = arrayOf("Current Location","Dubai", "Abu Dubai", "Moscow", "Kyiv", "Delhi","Bengaluru","Singapore", "Sydney", "London", "Amsterdam", "Paris", "Madrid", "Barcelona", "Miami",
        "Rome", "Boston", "New York", "San Diego", "Las Vegas")

        val spinnerAdapter: CustomDropDownAdapter = CustomDropDownAdapter(context!!, listItemsTxt)
        val spinner: Spinner = view?.findViewById(R.id.spinner) as Spinner
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position > 0) {
                    val city: String = listItemsTxt[position]
                    (activity as? AppCompatActivity)?.supportActionBar?.title = city
                    bindFetchedUI(city)
                } else{
                    currentLocation()
                }
            }
        }
    }

    private fun currentLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                val city = onLocationChanged(it)
                (activity as? AppCompatActivity)?.supportActionBar?.title = city
                bindFetchedUI(city)
            }
    }

    private fun bindFetchedUI(city: String) = launch {
       viewModel.city = city
        viewModel.getLatestDataForecast()
    }

    private fun setAdapter(){
        cityForecastAdapter = CityForecastAdapter(activity?.baseContext, listOf())
        recycler_view.layoutManager = LinearLayoutManager(activity?.baseContext)
        recycler_view.adapter = cityForecastAdapter
    }

    private fun bindUI() = launch {
        val cityForecast =  viewModel.cityForecast.await()
        cityForecast.observe(this@CurrentCityWeatherFragment, Observer {
            if(it.isNotEmpty()) {
                cityForecastAdapter.citiesWeatherList = it
                cityForecastAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun onLocationChanged(location: Location?) : String {
        var cityName: String  = "Bangalore"
        try {
            val gcd: Geocoder = Geocoder(activity?.baseContext, Locale.getDefault())
            var addresses: List<Address>? = null
            try {
                if (location != null) {
                    addresses = gcd.getFromLocation(
                        location.latitude,
                        location.longitude, 1
                    )
                }
                if (addresses?.isNotEmpty()!!) {
                    cityName = addresses[0].locality
                }
            } catch (e: IOException) {
            }
        }catch (e:IOException){}
        return cityName
    }
}
