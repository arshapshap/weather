package com.example.main.di

import com.example.main.data.repository.LocationRepositoryImpl
import com.example.main.data.repository.WeatherForecastRepositoryImpl
import com.example.main.data.repository.WeatherRepositoryImpl
import com.example.main.domain.repository.LocationRepository
import com.example.main.domain.repository.WeatherForecastRepository
import com.example.main.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module

@Module
abstract class AppBindsModule {

    @Binds
    abstract fun bindWeatherRepositoryImpl_to_WeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    abstract fun bindWeatherForecastRepositoryImpl_to_WeatherForecastRepository(weatherForecastRepositoryImpl: WeatherForecastRepositoryImpl): WeatherForecastRepository

    @Binds
    abstract fun bindLocationRepositoryImpl_to_LocationRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository
}