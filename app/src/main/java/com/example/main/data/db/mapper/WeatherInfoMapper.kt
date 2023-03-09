package com.example.main.db.mapper

import com.example.main.db.entity.WeatherInfoEntity
import com.example.main.domain.models.WeatherInfo
import java.util.*

object WeatherInfoMapper {
    fun map(weatherInfo: WeatherInfo): WeatherInfoEntity {
        with(weatherInfo) {
            return WeatherInfoEntity(
                cityName = cityName,
                temperatureInCelsius = temperatureInCelsius,
                humidityAsPercentage = humidityAsPercentage,
                pressureInMillimetersOfMercury = pressureInMillimetersOfMercury,
                windSpeedInMetersPerSecond = windSpeedInMetersPerSecond,
                imageUrl = imageUrl,
                lastUpdate = Calendar.getInstance().timeInMillis
            )
        }
    }

    fun map(weatherInfoEntity: WeatherInfoEntity): WeatherInfo {
        with(weatherInfoEntity) {
            return WeatherInfo(
                cityName,
                temperatureInCelsius,
                humidityAsPercentage,
                pressureInMillimetersOfMercury,
                windSpeedInMetersPerSecond,
                imageUrl
            )
        }
    }
}