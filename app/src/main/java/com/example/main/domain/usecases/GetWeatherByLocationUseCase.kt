package com.example.main.domain.usecases

import com.example.main.domain.models.WeatherInfo

class GetWeatherByLocationUseCase {
    fun execute(latitude: Double, longitude: Double) : WeatherInfo {
        return WeatherInfo(cityName = "Kazan", temperatureInCelsius = 0, humidityAsPercentage = 74, pressureInMillimetersOfMercury = 756, windSpeedInMetersPerSecond = 5.9f)
    }
}