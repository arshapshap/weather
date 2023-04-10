package com.example.main.db.dao

import androidx.room.*
import com.example.main.db.entity.WeatherInfoEntity

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWeatherInfo(weatherInfoEntity: WeatherInfoEntity)

    @Query("SELECT * FROM WEATHER WHERE city_name = :cityName")
    fun getWeatherInfoByCityName(cityName: String): WeatherInfoEntity?
}