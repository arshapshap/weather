package com.example.main

import android.app.Application
import com.example.main.di.AppComponent
import com.example.main.di.AppModule
import com.example.main.di.DaggerAppComponent

open class WeatherApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}