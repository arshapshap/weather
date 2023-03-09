package com.example.main.domain.usecases

import com.example.main.domain.IWeatherRepository
import com.example.main.domain.models.WeatherInfo

class GetWeatherByCityNameUseCase(private val weatherRepository: IWeatherRepository) {
    suspend fun execute(cityName: String): WeatherInfo {
        return weatherRepository.getWeatherByCityName(cityName)
    }
}