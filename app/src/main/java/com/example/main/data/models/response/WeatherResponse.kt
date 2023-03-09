package com.example.main.data.models.response


import com.example.main.data.network.WEATHER_IMAGES_BASE_URL
import com.example.main.domain.models.WeatherInfo
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("main")
    val main: Main?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("weather")
    val weather: List<Weather?>?,
    @SerializedName("wind")
    val wind: Wind?
) {
    fun toWeatherInfo() = WeatherInfo(
        cityName = name ?: "",
        temperatureInCelsius = main?.temp?.toInt() ?: 0,
        humidityAsPercentage = main?.humidity ?: 0,
        pressureInMillimetersOfMercury = main?.pressure ?: 0,
        windSpeedInMetersPerSecond = wind?.speed ?: 0.0,
        imageUrl = WEATHER_IMAGES_BASE_URL + "${weather?.first()?.icon}@2x.png")
}

data class Main(
    @SerializedName("humidity")
    val humidity: Int?,
    @SerializedName("pressure")
    val pressure: Int?,
    @SerializedName("temp")
    val temp: Double?,
)

data class Weather(
    @SerializedName("icon")
    val icon: String?,
)

data class Wind(
    @SerializedName("speed")
    val speed: Double?
)