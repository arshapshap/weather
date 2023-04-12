package com.example.main.data.repository

import com.example.main.data.mapper.WeatherForecastInfoMapper
import com.example.main.data.network.WeatherForecastApiService
import com.example.main.domain.models.LocationInfo
import com.example.main.domain.models.WeatherForecastInfo
import com.example.main.domain.repository.WeatherForecastRepository
import javax.inject.Inject

class WeatherForecastRepositoryImpl @Inject constructor(
    private val remoteSource: WeatherForecastApiService, private val mapper: WeatherForecastInfoMapper
) : WeatherForecastRepository {

    override suspend fun getWeatherForecastByLocation(locationInfo: LocationInfo): WeatherForecastInfo? {
        val weatherForecastResponse = remoteSource.getWeatherForecastByLocation(
            latitude = locationInfo.latitude, longitude = locationInfo.longitude
        )

        return mapper.map(weatherForecastResponse)
    }

}