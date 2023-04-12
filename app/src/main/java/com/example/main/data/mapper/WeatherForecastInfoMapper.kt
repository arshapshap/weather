package com.example.main.data.mapper

import com.example.main.data.network.response.WeatherForecastResponse
import com.example.main.domain.models.WeatherForecastInfo
import javax.inject.Inject

class WeatherForecastInfoMapper @Inject constructor(
    private val weatherInfoMapper: WeatherInfoMapper
) {
    fun map(weatherForecastResponse: WeatherForecastResponse) : WeatherForecastInfo {
        with (weatherForecastResponse) {
            return WeatherForecastInfo(
                list = list?.filterNotNull()?.map { weatherInfoMapper.map(it) } ?: listOf()
            )
        }
    }
}