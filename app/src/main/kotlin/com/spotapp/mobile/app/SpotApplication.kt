package com.spotapp.mobile.app

import android.app.Application
import com.spotapp.mobile.app.di.AppModule

class SpotApplication : Application() {

    lateinit var appModule: AppModule

    override fun onCreate() {
        super.onCreate()
        appModule = AppModule(this)
    }
}
