package com.example.main.data.di

import com.example.main.data.location.LocationDataSource
import com.example.main.data.mapper.LocationInfoMapper
import com.example.main.data.mapper.WeatherInfoMapper
import com.example.main.data.network.WeatherApiService
import com.example.main.data.repository.LocationRepositoryImpl
import com.example.main.data.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class, DatabaseModule::class])
class DataModule {

    @Provides
    fun provideWeatherRepository(
        weatherApiService: WeatherApiService,
        weatherInfoMapper: WeatherInfoMapper
    ): WeatherRepositoryImpl {
        return WeatherRepositoryImpl(
            remoteSource = weatherApiService, mapper = weatherInfoMapper
        )
    }

    @Provides
    fun provideLocationRepository(
        locationDataSource: LocationDataSource,
        locationInfoMapper: LocationInfoMapper
    ): LocationRepositoryImpl {
        return LocationRepositoryImpl(
            dataSource = locationDataSource, mapper = locationInfoMapper
        )
    }
}