package com.example.main.presentation.fragments.mainFragment

import androidx.lifecycle.*
import com.example.main.R
import com.example.main.domain.models.LocationInfo
import com.example.main.domain.models.WeatherInfo
import com.example.main.domain.usecases.GetLocationUseCase
import com.example.main.domain.usecases.GetWeatherByCityNameUseCase
import com.example.main.domain.usecases.GetWeatherByLocationUseCase
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

class MainViewModel @AssistedInject constructor(
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
    private val getWeatherByLocationUseCase: GetWeatherByLocationUseCase,
    private val getLocationUseCase: GetLocationUseCase
) : ViewModel() {

    private val weatherInfoLiveMutable = MutableLiveData<WeatherInfo?>(null)
    val weatherInfoLive: LiveData<WeatherInfo?> = weatherInfoLiveMutable

    private val loadingLiveMutable = MutableLiveData(false)
    val loadingLive: LiveData<Boolean> = loadingLiveMutable

    private val errorLiveMutable = MutableLiveData<Int?>(null)
    val errorLive: LiveData<Int?> = errorLiveMutable

    fun getWeatherByCityName(cityName: String) {
        weatherInfoLiveMutable.postValue(null)
        loadingLiveMutable.postValue(true)
        errorLiveMutable.postValue(null)

        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                return@runCatching getWeatherByCityNameUseCase.execute(cityName)
            }.onSuccess {
                weatherInfoLiveMutable.postValue(it)
            }.onFailure {
                errorLiveMutable.postValue(when (it) {
                    is UnknownHostException -> R.string.network_unavailable
                    is HttpException -> R.string.city_not_found
                    else -> null
                })
            }.also {
                loadingLiveMutable.postValue(false)
            }
        }
    }

    fun getWeatherByLocation() {
        weatherInfoLiveMutable.postValue(null)
        loadingLiveMutable.postValue(true)
        errorLiveMutable.postValue(null)

        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                val location = getLocationUseCase.execute()
                if (location != null) {
                    return@runCatching getWeatherByLocationUseCase.execute(
                        LocationInfo(
                            location.latitude, location.longitude
                        )
                    )
                } else throw NullPointerException("Location unavailable")
            }.onSuccess {
                weatherInfoLiveMutable.postValue(it)
            }.onFailure {
                errorLiveMutable.postValue(when (it) {
                    is NullPointerException -> R.string.location_unavailable
                    else -> null
                })
            }.also {
                loadingLiveMutable.postValue(false)
            }
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(): MainViewModel
    }
}