package com.example.main.data.di

import android.content.Context
import com.example.main.BuildConfig
import com.example.main.R
import com.example.main.data.network.WeatherApiService
import com.example.main.data.network.WeatherForecastApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
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
    fun provideWeatherForecastApiService(@Named("Forecast") retrofit: Retrofit): WeatherForecastApiService {
        return retrofit.create(
            WeatherForecastApiService::class.java
        )
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.WEATHER_BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    @Named("Forecast")
    fun provideRetrofitForecastInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.WEATHER_BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    fun provideOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
            val modifiedUrl = chain.request().url().newBuilder().addQueryParameter(
                "appid", BuildConfig.WEATHER_API_KEY
            ).addQueryParameter(
                "units", "metric"
            ).addQueryParameter(
                "lang", context.getString(R.string.language_code)
            ).build()

            val request = chain.request().newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }.build()
    }
}