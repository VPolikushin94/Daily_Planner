package com.example.simbirsoft.core

import android.app.Application
import com.example.simbirsoft.di.dataModule
import com.example.simbirsoft.di.interactorModule
import com.example.simbirsoft.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(dataModule, interactorModule, viewModelModule)
        }
    }
}