package com.example.main.data.network.response

import com.google.gson.annotations.SerializedName

data class WeatherForecastResponse(
    @SerializedName("list") val list: List<WeatherResponse?>?
)
