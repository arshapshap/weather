package com.example.main.data.repository

import com.example.main.data.db.DatabaseHandler
import com.example.main.data.network.WeatherApiContainer
import com.example.main.data.mapper.WeatherInfoMapper
import com.example.main.domain.IWeatherRepository
import com.example.main.domain.models.LocationInfo
import com.example.main.domain.models.WeatherInfo
import java.util.*

class WeatherRepository : IWeatherRepository {
    override suspend fun getWeatherByCityName(cityName: String): WeatherInfo? {
        val lastUpdate = DatabaseHandler.getLastUpdateByCityName(cityName)

        if (Calendar.getInstance().timeInMillis - lastUpdate >= 60 * 1000) {
            val weatherResponse = WeatherApiContainer.weatherApi.getWeatherByCityName(cityName)
            val weatherInfo = WeatherInfoMapper.map(weatherResponse)

            DatabaseHandler.addWeatherInfo(weatherInfo)

            return weatherInfo
        }

        return DatabaseHandler.getWeatherInfoByCityName(cityName)
    }

    override suspend fun getWeatherByLocation(location: LocationInfo): WeatherInfo {
        val weatherResponse = WeatherApiContainer.weatherApi.getWeatherByLocation(
            latitude = location.latitude,
            longitude = location.longitude
        )
        val weatherInfo = WeatherInfoMapper.map(weatherResponse)

        DatabaseHandler.addWeatherInfo(weatherInfo)

        return weatherInfo
    }
}