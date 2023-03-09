package com.example.main.domain.usecases

import com.example.main.domain.IWeatherRepository
import com.example.main.domain.models.LocationInfo
import com.example.main.domain.models.WeatherInfo

class GetWeatherByLocationUseCase(private val weatherRepository: IWeatherRepository) {
    suspend fun execute(locationInfo: LocationInfo): WeatherInfo? {
        return weatherRepository.getWeatherByLocation(locationInfo)
    }
}