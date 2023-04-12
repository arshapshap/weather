package com.example.main.domain.repository

import com.example.main.domain.models.LocationInfo
import com.example.main.domain.models.WeatherForecastInfo

interface WeatherForecastRepository {

    suspend fun getWeatherForecastByLocation(locationInfo: LocationInfo): WeatherForecastInfo?
}