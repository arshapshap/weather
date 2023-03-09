package com.example.main.presentation.fragments.mainFragment

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.main.domain.usecases.GetWeatherByCityNameUseCase
import com.example.main.domain.usecases.GetWeatherByLocationUseCase
import com.example.main.presentation.providers.LocationProvider

class MainViewModelFactory(private val activity: Activity) : ViewModelProvider.Factory {
    private val getWeatherByCityNameUseCase by lazy {
        GetWeatherByCityNameUseCase()
    }
    private val getWeatherByLocationUseCase by lazy {
        GetWeatherByLocationUseCase()
    }
    private val locationProvider by lazy {
        LocationProvider(activity as AppCompatActivity)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            getWeatherByCityNameUseCase,
            getWeatherByLocationUseCase,
            locationProvider
        ) as T
    }
}