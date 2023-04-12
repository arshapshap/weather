package com.example.main.data.network.response


import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("coord") val coordinates: Coordinates?,
    @SerializedName("main") val main: Main?,
    @SerializedName("name") val name: String?,
    @SerializedName("weather") val weather: List<Weather?>?,
    @SerializedName("wind") val wind: Wind?,
    @SerializedName("dt") val date: Long?
)

data class Coordinates(
    @SerializedName("lat") val latitude: Double?,
    @SerializedName("lon") val longitude: Double?,
)

data class Main(
    @SerializedName("humidity") val humidity: Int?,
    @SerializedName("pressure") val pressure: Int?,
    @SerializedName("temp") val temp: Double?,
)

data class Weather(
    @SerializedName("icon") val icon: String?,
)

data class Wind(
    @SerializedName("speed") val speed: Double?
)