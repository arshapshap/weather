package com.example.main.data.db

import android.content.Context
import androidx.room.Room
import com.example.main.db.mapper.WeatherInfoMapper
import com.example.main.domain.models.WeatherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DatabaseHandler {

    const val databaseVersion = 1

    private var roomDatabase: AppDatabase? = null

    fun dbInitialize(appContext: Context) {
        if (roomDatabase == null)
            roomDatabase = Room.databaseBuilder(appContext, AppDatabase::class.java, "sampleDB")
                .build()
    }

    suspend fun addWeatherInfo(weatherInfo: WeatherInfo) {
        withContext(Dispatchers.IO) {
            roomDatabase?.getUserDao()?.addWeatherInfo(WeatherInfoMapper.map(weatherInfo))
        }
    }

    suspend fun getWeatherInfoByCityName(cityName: String) : WeatherInfo? {
        val weatherInfoEntity = withContext(Dispatchers.IO) {
            roomDatabase?.getUserDao()?.getWeatherInfoByCityName(cityName)
        } ?: return null
        return WeatherInfoMapper.map(weatherInfoEntity)
    }

    suspend fun getLastUpdateByCityName(cityName: String) : Long {
        val weatherInfoEntity = withContext(Dispatchers.IO) {
            roomDatabase?.getUserDao()?.getWeatherInfoByCityName(cityName)
        }
        return weatherInfoEntity?.lastUpdate ?: 0
    }
}