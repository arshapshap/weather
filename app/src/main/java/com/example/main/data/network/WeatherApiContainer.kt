package com.example.main.data.network

import com.example.main.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val WEATHER_API_KEY = "6fb29eef060cdcd9461fa64de495357c"
private const val WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"
const val WEATHER_IMAGES_BASE_URL = "https://openweathermap.org/img/wn/"

object WeatherApiContainer {
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val modifiedUrl = chain.request().url().newBuilder()
                    .addQueryParameter("appid", WEATHER_API_KEY)
                    .addQueryParameter("units", "metric")
                    .build()

                val request = chain.request().newBuilder().url(modifiedUrl).build()
                chain.proceed(request)
            }
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .client(okHttpClient ?: OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherApi: WeatherApiService = retrofit.create(WeatherApiService::class.java)
}