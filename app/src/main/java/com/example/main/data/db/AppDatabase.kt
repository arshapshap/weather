package com.example.main.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.main.db.dao.WeatherDao
import com.example.main.db.entity.WeatherInfoEntity

@Database(
    entities = [WeatherInfoEntity::class],
    version = DatabaseHandler.DATABASE_VERSION,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): WeatherDao
}