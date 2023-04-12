package com.example.main.domain.usecases

import com.example.main.domain.models.WeatherInfo
import com.example.main.domain.repository.WeatherRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetWeatherByCityNameUseCase @Inject constructor(private val repository: WeatherRepository) {

    suspend fun execute(cityName: String): WeatherInfo? {
        return repository.getWeatherByCityName(cityName)
    }
}