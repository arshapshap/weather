package com.example.main.domain.usecases

import com.example.main.domain.models.LocationInfo
import com.example.main.domain.models.WeatherInfo
import com.example.main.domain.repository.WeatherRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetWeatherByLocationUseCase @Inject constructor(private val repository: WeatherRepository) {

    suspend fun execute(locationInfo: LocationInfo): WeatherInfo? {
        return repository.getWeatherByLocation(locationInfo)
    }
}