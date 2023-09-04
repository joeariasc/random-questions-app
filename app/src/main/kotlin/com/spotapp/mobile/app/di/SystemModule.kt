package com.spotapp.mobile.app.di

import android.content.Context
import android.location.LocationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SystemModule {
    @Provides
    fun provideContext(
        @ApplicationContext context: Context,
    ): Context = context

    @Provides
    fun provideLocationManager(
        @ApplicationContext context: Context,
    ): LocationManager = context.getSystemService(LocationManager::class.java)

}
