package com.spotapp.mobile.app.di

import android.content.Context
import android.location.LocationManager

class SystemModule(context: Context) {

    val applicationContext: Context = context.applicationContext

    val locationManager: LocationManager
        get() = applicationContext.getSystemService(LocationManager::class.java)
}
