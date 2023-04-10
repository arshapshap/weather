package com.example.main.presentation.fragments.mainFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.main.domain.models.LocationInfo
import com.example.main.domain.models.WeatherInfo
import com.example.main.domain.usecases.GetLocationUseCase
import com.example.main.domain.usecases.GetWeatherByCityNameUseCase
import com.example.main.domain.usecases.GetWeatherByLocationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
    private val getWeatherByLocationUseCase: GetWeatherByLocationUseCase,
    private val getLocationUseCase: GetLocationUseCase
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
            kotlin.runCatching {
                return@runCatching getWeatherByCityNameUseCase.execute(cityName)
            }.onSuccess {
                weatherInfoLiveMutable.postValue(it)
            }.onFailure {
                errorLiveMutable.postValue(true)
            }.also {
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
                val location = getLocationUseCase.execute()
                if (location != null) {
                    val response = getWeatherByLocationUseCase.execute(
                        LocationInfo(
                            location.latitude, location.longitude
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