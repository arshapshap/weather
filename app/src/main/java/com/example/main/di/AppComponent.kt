package com.example.main.di

import com.example.main.data.di.DataModule
import com.example.main.presentation.di.PresentationModule
import com.example.main.presentation.fragments.mainFragment.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, PresentationModule::class, AppBindsModule::class, AppModule::class])
interface AppComponent {

    fun inject(mainFragment: MainFragment)
}