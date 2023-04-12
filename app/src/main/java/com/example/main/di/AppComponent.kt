package com.example.main.di

import com.example.main.data.di.DataModule
import com.example.main.presentation.fragments.detailsBottomSheetFragment.DetailsBottomSheetFragment
import com.example.main.presentation.fragments.detailsBottomSheetFragment.DetailsViewModel
import com.example.main.presentation.fragments.mainFragment.MainFragment
import com.example.main.presentation.fragments.mainFragment.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, AppBindsModule::class, AppModule::class])
interface AppComponent {

    fun inject(mainFragment: MainFragment)

    fun inject(detailsBottomSheetFragment: DetailsBottomSheetFragment)

    fun detailsViewModel(): DetailsViewModel.Factory

    fun mainViewModel(): MainViewModel.Factory
}