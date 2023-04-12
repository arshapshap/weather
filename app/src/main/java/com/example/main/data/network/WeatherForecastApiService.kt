package com.example.main.data.network

import com.example.main.data.network.response.WeatherForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastApiService {

    @GET("forecast")
    suspend fun getWeatherForecastByLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): WeatherForecastResponse
}