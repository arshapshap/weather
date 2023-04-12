package com.example.main.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Weather")
data class WeatherInfoEntity(
    @ColumnInfo(name = "city_name") @PrimaryKey var cityName: String,
    @ColumnInfo(name = "temperature_in_celsius") var temperatureInCelsius: Int,
    @ColumnInfo(name = "humidity_as_percentage") var humidityAsPercentage: Int,
    @ColumnInfo(name = "pressure_in_millimeters_of_mercury") var pressureInMillimetersOfMercury: Int,
    @ColumnInfo(name = "wind_speed_in_meters_per_second") var windSpeedInMetersPerSecond: Double,
    @ColumnInfo(name = "image_url") var imageUrl: String,
    @ColumnInfo(name = "latitude") var latitude: Double?,
    @ColumnInfo(name = "longitude") var longitude: Double?,
    @ColumnInfo(name = "last_update") var lastUpdate: Long,
)