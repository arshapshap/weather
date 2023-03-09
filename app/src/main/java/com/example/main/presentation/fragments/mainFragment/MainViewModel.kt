package com.example.main.presentation.fragments.mainFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.main.domain.models.LocationInfo
import com.example.main.domain.models.WeatherInfo
import com.example.main.domain.usecases.GetWeatherByCityNameUseCase
import com.example.main.domain.usecases.GetWeatherByLocationUseCase
import com.example.main.presentation.providers.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.NullPointerException

class MainViewModel(
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
    private val getWeatherByLocationUseCase: GetWeatherByLocationUseCase,
    private val locationProvider: LocationProvider
) : ViewModel() {

    private val weatherInfoLiveMutable = MutableLiveData<WeatherInfo?>(null)
    val weatherInfoLive: LiveData<WeatherInfo?> = weatherInfoLiveMutable

    private val loadingLiveMutable = MutableLiveData(false)
    val loadingLive: LiveData<Boolean> = loadingLiveMutable

    private val errorLiveMutable = MutableLiveData(false)
    val errorLive: LiveData<Boolean> = errorLiveMutable

    fun getWeatherByCityName(cityName: String) {
        loadingLiveMutable.value = true
        weatherInfoLiveMutable.value = null
        errorLiveMutable.value = false

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getWeatherByCityNameUseCase.execute(cityName)
                weatherInfoLiveMutable.postValue(response)
            } catch (e: Exception) {
                Log.e("AAA", e.toString())
                errorLiveMutable.postValue(true)
            } finally {
                loadingLiveMutable.postValue(false)
            }
        }
    }

    fun getWeatherByLocation() {
        loadingLiveMutable.value = true
        weatherInfoLiveMutable.value = null
        errorLiveMutable.value = false

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val location = locationProvider.getCurrentLocation()
                if (location != null) {
                    val response = getWeatherByLocationUseCase.execute(
                        LocationInfo(
                            location.latitude,
                            location.longitude
                        )
                    )
                    weatherInfoLiveMutable.postValue(response)
                } else throw NullPointerException("Location unavailable")
            } catch (e: Exception) {
                errorLiveMutable.postValue(true)
            } finally {
                loadingLiveMutable.postValue(false)
            }
        }
    }
}