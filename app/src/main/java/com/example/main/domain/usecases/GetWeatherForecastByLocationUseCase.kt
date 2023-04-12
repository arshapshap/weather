package com.example.main.domain.usecases

import com.example.main.domain.models.LocationInfo
import com.example.main.domain.models.WeatherForecastInfo
import com.example.main.domain.repository.WeatherForecastRepository
import javax.inject.Inject

class GetWeatherForecastByLocationUseCase @Inject constructor(private val repository: WeatherForecastRepository) {

    suspend fun execute(locationInfo: LocationInfo): WeatherForecastInfo? {
        return repository.getWeatherForecastByLocation(locationInfo)
    }
}