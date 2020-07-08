package com.naman.weather.data.db.entites


import com.google.gson.annotations.SerializedName

data class Sys(
    val country: String,
    val id: Int,
    val message: Double,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)