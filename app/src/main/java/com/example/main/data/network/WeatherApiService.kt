package com.example.main.data.network

import com.example.main.data.network.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("weather")
    suspend fun getWeatherByCityName(@Query("q") cityName: String): WeatherResponse

    @GET("weather")
    suspend fun getWeatherByLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): WeatherResponse
}