package com.example.main.domain.usecases

import com.example.main.domain.models.WeatherInfo

class GetWeatherByCityNameUseCase {
    fun execute(cityName: String) : WeatherInfo {
        return WeatherInfo(cityName = "Kazan", temperatureInCelsius = 0, humidityAsPercentage = 74, pressureInMillimetersOfMercury = 756, windSpeedInMetersPerSecond = 5.9f)
    }
}