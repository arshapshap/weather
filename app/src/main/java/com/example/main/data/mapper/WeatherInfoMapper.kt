package com.example.main.data.mapper

import com.example.main.BuildConfig.WEATHER_IMAGES_BASE_URL
import com.example.main.data.db.entity.WeatherInfoEntity
import com.example.main.data.network.response.WeatherResponse
import com.example.main.domain.models.LocationInfo
import com.example.main.domain.models.WeatherInfo
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class WeatherInfoMapper @Inject constructor() {

    fun map(weatherInfo: WeatherInfo): WeatherInfoEntity {
        with(weatherInfo) {
            return WeatherInfoEntity(
                cityName = cityName,
                temperatureInCelsius = temperatureInCelsius,
                humidityAsPercentage = humidityAsPercentage,
                pressureInMillimetersOfMercury = pressureInMillimetersOfMercury,
                windSpeedInMetersPerSecond = windSpeedInMetersPerSecond,
                imageUrl = imageUrl,
                latitude = locationInfo.latitude,
                longitude = locationInfo.longitude,
                lastUpdate = Calendar.getInstance().timeInMillis
            )
        }
    }

    fun map(weatherInfoEntity: WeatherInfoEntity): WeatherInfo {
        with(weatherInfoEntity) {
            return WeatherInfo(
                cityName = cityName,
                temperatureInCelsius = temperatureInCelsius,
                humidityAsPercentage = humidityAsPercentage,
                pressureInMillimetersOfMercury = pressureInMillimetersOfMercury,
                windSpeedInMetersPerSecond = windSpeedInMetersPerSecond,
                imageUrl = imageUrl,
                locationInfo = LocationInfo(
                    latitude = latitude ?: 0.0,
                    longitude = longitude ?: 0.0
                ),
                date = null
            )
        }
    }

    fun map(weatherResponse: WeatherResponse): WeatherInfo {
        with(weatherResponse) {
            return WeatherInfo(
                cityName = name ?: "",
                temperatureInCelsius = main?.temp?.toInt() ?: 0,
                humidityAsPercentage = main?.humidity ?: 0,
                pressureInMillimetersOfMercury = ((main?.pressure ?: 0) * 0.75).roundToInt(),
                windSpeedInMetersPerSecond = wind?.speed ?: 0.0,
                imageUrl = WEATHER_IMAGES_BASE_URL + "${weather?.first()?.icon}@2x.png",
                locationInfo = LocationInfo(
                    latitude = coordinates?.latitude ?: 0.0,
                    longitude = coordinates?.longitude ?: 0.0
                ),
                date = date?.let { Date(it * 1000) }
            )
        }
    }
}