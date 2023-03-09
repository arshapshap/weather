package com.example.main.domain.models

import java.io.Serializable

data class WeatherInfo(
    val cityName: String,
    val temperatureInCelsius: Int,
    val humidityAsPercentage: Int,
    val pressureInMillimetersOfMercury: Int,
    val windSpeedInMetersPerSecond: Double,
    val imageUrl: String
) : Serializable
