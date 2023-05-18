package com.example.main.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Weather")
data class WeatherInfoEntity(
    @ColumnInfo(name = "city_name") @PrimaryKey val cityName: String,
    @ColumnInfo(name = "temperature_in_celsius") val temperatureInCelsius: Int,
    @ColumnInfo(name = "humidity_as_percentage") val humidityAsPercentage: Int,
    @ColumnInfo(name = "pressure_in_millimeters_of_mercury") val pressureInMillimetersOfMercury: Int,
    @ColumnInfo(name = "wind_speed_in_meters_per_second") val windSpeedInMetersPerSecond: Double,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "latitude") val latitude: Double?,
    @ColumnInfo(name = "longitude") val longitude: Double?,
    @ColumnInfo(name = "last_update") val lastUpdate: Long,
)