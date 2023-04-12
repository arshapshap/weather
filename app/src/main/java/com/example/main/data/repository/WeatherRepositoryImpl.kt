package com.example.main.data.repository

import com.example.main.data.db.DatabaseHandler
import com.example.main.data.mapper.WeatherInfoMapper
import com.example.main.data.network.WeatherApiService
import com.example.main.domain.models.LocationInfo
import com.example.main.domain.models.WeatherInfo
import com.example.main.domain.repository.WeatherRepository
import java.util.*
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteSource: WeatherApiService,
    private val mapper: WeatherInfoMapper
) : WeatherRepository {

    override suspend fun getWeatherByCityName(cityName: String): WeatherInfo? {
        val lastUpdate = DatabaseHandler.getLastUpdateByCityName(cityName)

        if (Calendar.getInstance().timeInMillis - lastUpdate >= 60 * 1000) {
            val weatherResponse = remoteSource.getWeatherByCityName(cityName)
            val weatherInfo = mapper.map(weatherResponse)

            DatabaseHandler.addWeatherInfoEntity(mapper.map(weatherInfo))

            return weatherInfo
        }

        val weatherInfoEntity = DatabaseHandler.getWeatherInfoByCityName(cityName)

        return weatherInfoEntity?.let { mapper.map(it) }
    }

    override suspend fun getWeatherByLocation(location: LocationInfo): WeatherInfo? {
        val weatherResponse = remoteSource.getWeatherByLocation(
            latitude = location.latitude, longitude = location.longitude
        )
        val weatherInfo = mapper.map(weatherResponse)

        DatabaseHandler.addWeatherInfoEntity(mapper.map(weatherInfo))

        return weatherInfo
    }
}