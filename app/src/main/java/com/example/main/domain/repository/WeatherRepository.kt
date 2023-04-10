package com.example.main.domain.repository

import com.example.main.domain.models.LocationInfo
import com.example.main.domain.models.WeatherInfo

interface WeatherRepository {

    suspend fun getWeatherByCityName(cityName: String): WeatherInfo?

    suspend fun getWeatherByLocation(location: LocationInfo): WeatherInfo?
}