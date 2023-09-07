package com.spotapp.mobile.app.di

import android.content.Context

class AppModule(context: Context) {
    private val system: SystemModule = SystemModule(context)
    private val data: DataModule = DataModule(system)
    val domain: DomainModule = DomainModule(data)
}
