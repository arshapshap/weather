package com.example.main.data.repository

import com.example.main.data.network.WeatherApiContainer
import com.example.main.domain.IWeatherRepository
import com.example.main.domain.models.LocationInfo
import com.example.main.domain.models.WeatherInfo

class WeatherRepository : IWeatherRepository {
    override suspend fun getWeatherByCityName(cityName: String): WeatherInfo {
        val weatherResponse = WeatherApiContainer.weatherApi.getWeatherByCityName(cityName)
        return weatherResponse.toWeatherInfo()
    }

    override suspend fun getWeatherByLocation(location: LocationInfo): WeatherInfo {
        val weatherResponse = WeatherApiContainer.weatherApi.getWeatherByLocation(
            latitude = location.latitude,
            longitude = location.longitude
        )
        return weatherResponse.toWeatherInfo()
    }
}