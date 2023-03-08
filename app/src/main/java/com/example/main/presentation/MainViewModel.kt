package com.example.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.main.domain.models.WeatherInfo
import com.example.main.domain.usecases.GetWeatherByCityNameUseCase
import com.example.main.domain.usecases.GetWeatherByLocationUseCase

class MainViewModel : ViewModel() {
    private val getWeatherByCityNameUseCase by lazy {
        GetWeatherByCityNameUseCase()
    }
    private val getWeatherByLocationUseCase by lazy {
        GetWeatherByLocationUseCase()
    }

    private val weatherInfoLiveMutable = MutableLiveData<WeatherInfo>()
    val weatherInfoLive: LiveData<WeatherInfo> = weatherInfoLiveMutable

    fun getWeatherByCityName(cityName: String) {
        weatherInfoLiveMutable.value = getWeatherByCityNameUseCase.execute(cityName)
    }

    fun getWeatherByLocation(latitude: Int, longitude: Int) {
        weatherInfoLiveMutable.value = getWeatherByLocationUseCase.execute(latitude, longitude)
    }
}