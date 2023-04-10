package com.example.main.presentation.di

import com.example.main.domain.usecases.GetLocationUseCase
import com.example.main.domain.usecases.GetWeatherByCityNameUseCase
import com.example.main.domain.usecases.GetWeatherByLocationUseCase
import com.example.main.presentation.fragments.mainFragment.MainViewModel
import com.example.main.presentation.fragments.mainFragment.MainViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    fun provideMainViewModel(mainViewModelFactory: MainViewModelFactory): MainViewModel {
        return mainViewModelFactory.create(MainViewModel::class.java)
    }

    @Provides
    fun provideMainViewModelFactory(
        getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
        getWeatherByLocationUseCase: GetWeatherByLocationUseCase,
        getLocationUseCase: GetLocationUseCase
    ): MainViewModelFactory {
        return MainViewModelFactory(
            getWeatherByCityNameUseCase = getWeatherByCityNameUseCase,
            getWeatherByLocationUseCase = getWeatherByLocationUseCase,
            getLocationUseCase = getLocationUseCase
        )
    }
}