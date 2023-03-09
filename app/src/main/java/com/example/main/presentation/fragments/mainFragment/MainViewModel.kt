package com.example.main.presentation.fragments.mainFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.main.domain.models.WeatherInfo
import com.example.main.domain.usecases.GetWeatherByCityNameUseCase
import com.example.main.domain.usecases.GetWeatherByLocationUseCase
import com.example.main.presentation.providers.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
    private val getWeatherByLocationUseCase: GetWeatherByLocationUseCase,
    private val locationProvider: LocationProvider
) : ViewModel() {

    private val weatherInfoLiveMutable = MutableLiveData<WeatherInfo?>(null)
    val weatherInfoLive: LiveData<WeatherInfo?> = weatherInfoLiveMutable

    private val loadingLiveMutable = MutableLiveData(false)
    val loadingLive: LiveData<Boolean> = loadingLiveMutable

    fun getWeatherByCityName(cityName: String) {
        weatherInfoLiveMutable.value = getWeatherByCityNameUseCase.execute(cityName)
    }

    fun getWeatherByLocation() {
        loadingLiveMutable.value = true
        weatherInfoLiveMutable.value = null

        viewModelScope.launch(Dispatchers.IO) {
            val location = locationProvider.getCurrentLocation()
            if (location != null)
                weatherInfoLiveMutable.postValue(getWeatherByLocationUseCase.execute(location.latitude, location.longitude))
            loadingLiveMutable.postValue(false)
        }
    }
}