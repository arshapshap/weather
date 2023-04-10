package com.example.main.data.di

import com.example.main.BuildConfig
import com.example.main.data.network.WeatherApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideWeatherApiService(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(
            WeatherApiService::class.java
        )
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.WEATHER_BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
            val modifiedUrl = chain.request().url().newBuilder().addQueryParameter(
                "appid", BuildConfig.WEATHER_API_KEY
            ).addQueryParameter(
                "units", "metric"
            ).build()

            val request = chain.request().newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }.build()
    }
}