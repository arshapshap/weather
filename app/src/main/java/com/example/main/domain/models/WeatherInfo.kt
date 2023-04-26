package com.example.main.domain.models

import java.io.Serializable
import java.util.*

data class WeatherInfo(
    val cityName: String,
    val temperatureInCelsius: Int,
    val humidityAsPercentage: Int,
    val pressureInMillimetersOfMercury: Int,
    val windSpeedInMetersPerSecond: Double,
    val imageUrl: String,
    val locationInfo: LocationInfo,
    val date: Date?
) : Serializable