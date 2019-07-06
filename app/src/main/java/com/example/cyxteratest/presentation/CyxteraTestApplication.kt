package com.example.cyxteratest.presentation

import android.app.Application
import android.content.Context
import com.example.cyxteratest.presentation.di.daosModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class CyxteraTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        startKoin {
            androidContext(appContext)
            modules(daosModule)
        }
    }

    companion object {
        lateinit var appContext: Context
    }
}