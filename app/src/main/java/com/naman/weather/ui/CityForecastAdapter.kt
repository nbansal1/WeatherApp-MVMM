package com.naman.weather.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naman.weather.R
import com.naman.weather.data.db.entites.WeatherResponse
import com.naman.weather.data.db.entites.forecast.CityForecast
import com.naman.weather.data.network.response.CitiesWeatherResponse
import kotlinx.android.synthetic.main.cities_item_layout.view.*
import kotlinx.android.synthetic.main.cities_item_layout.view.tvCityHumidity
import kotlinx.android.synthetic.main.cities_item_layout.view.tvCityMaxTemp
import kotlinx.android.synthetic.main.cities_item_layout.view.tvCityMinTemp
import kotlinx.android.synthetic.main.cities_item_layout.view.tvCityPressure
import kotlinx.android.synthetic.main.cities_item_layout.view.tvCityTemp
import kotlinx.android.synthetic.main.cities_item_layout.view.tvCityWindSpeed
import kotlinx.android.synthetic.main.city_forecast_layout.view.*

class CityForecastAdapter(
    val context: Context?,
    var citiesWeatherList : List<CityForecast>

): RecyclerView.Adapter<CityForecastAdapter.CitiesWeatherViewHolder>() {
    class CitiesWeatherViewHolder(item: View) : RecyclerView.ViewHolder(item){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesWeatherViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.city_forecast_layout,parent,false)
        return CitiesWeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return citiesWeatherList.size
    }

    override fun onBindViewHolder(holder: CitiesWeatherViewHolder, position: Int) {
        var citiesWeather = citiesWeatherList.get(position)
        holder.itemView.tvCityTemp.text = "Temp - ${citiesWeather.main.temp} C "
        holder.itemView.tvTime.text = "${citiesWeather.dtTxt}"
        holder.itemView.tvCityMinTemp.text = "Min - ${citiesWeather.main.tempMin} C "
        holder.itemView.tvCityMaxTemp.text = "Max - ${citiesWeather.main.tempMax} C "
        holder.itemView.tvCityWindSpeed.text = "Wind Speed - ${citiesWeather.wind.speed}"
        holder.itemView.tvCityHumidity.text = "Max - ${citiesWeather.main.humidity}"
        holder.itemView.tvCityPressure.text = "Max - ${citiesWeather.main.pressure} "
    }
}