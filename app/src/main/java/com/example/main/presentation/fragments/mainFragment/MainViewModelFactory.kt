package com.example.main.presentation.fragments.mainFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.main.domain.usecases.GetLocationUseCase
import com.example.main.domain.usecases.GetWeatherByCityNameUseCase
import com.example.main.domain.usecases.GetWeatherByLocationUseCase

class MainViewModelFactory(
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
    private val getWeatherByLocationUseCase: GetWeatherByLocationUseCase,
    private val getLocationUseCase: GetLocationUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                getWeatherByCityNameUseCase = getWeatherByCityNameUseCase,
                getWeatherByLocationUseCase = getWeatherByLocationUseCase,
                getLocationUseCase = getLocationUseCase
            ) as T

            else -> throw IllegalArgumentException("Unable to construct viewModel")
        }
    }
}