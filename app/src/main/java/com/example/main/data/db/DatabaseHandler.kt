package com.example.main.data.db

import android.content.Context
import androidx.room.Room
import com.example.main.db.entity.WeatherInfoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DatabaseHandler {

    const val DATABASE_VERSION = 1

    private const val DATABASE_NAME = "weatherDB"
    private var roomDatabase: AppDatabase? = null

    fun dbInitialize(appContext: Context) {
        if (roomDatabase == null) roomDatabase = Room.databaseBuilder(
            appContext, AppDatabase::class.java, DATABASE_NAME
        ).build()
    }

    suspend fun addWeatherInfoEntity(weatherInfoEntity: WeatherInfoEntity) {
        withContext(Dispatchers.IO) {
            roomDatabase?.getUserDao()?.addWeatherInfo(weatherInfoEntity)
        }
    }

    suspend fun getWeatherInfoByCityName(cityName: String): WeatherInfoEntity? {
        val weatherInfoEntity = withContext(Dispatchers.IO) {
            roomDatabase?.getUserDao()?.getWeatherInfoByCityName(cityName)
        } ?: return null
        return weatherInfoEntity
    }

    suspend fun getLastUpdateByCityName(cityName: String): Long {
        val weatherInfoEntity = withContext(Dispatchers.IO) {
            roomDatabase?.getUserDao()?.getWeatherInfoByCityName(cityName)
        }
        return weatherInfoEntity?.lastUpdate ?: 0
    }
}