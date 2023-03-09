package com.example.main.domain

import com.example.main.domain.models.LocationInfo
import com.example.main.domain.models.WeatherInfo

interface IWeatherRepository {
    suspend fun getWeatherByCityName(cityName: String) : WeatherInfo?

    suspend fun getWeatherByLocation(location: LocationInfo) : WeatherInfo?
}