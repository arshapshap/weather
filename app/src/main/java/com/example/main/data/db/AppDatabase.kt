package com.example.main.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.main.db.dao.WeatherDao
import com.example.main.db.entity.WeatherInfoEntity

@Database(
    entities = [WeatherInfoEntity::class], version = DatabaseHandler.DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): WeatherDao
}