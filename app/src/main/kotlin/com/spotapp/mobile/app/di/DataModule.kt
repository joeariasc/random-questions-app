package com.spotapp.mobile.app.di

import android.content.Context
import com.spotapp.mobile.data.repository.UsersRepository
import com.spotapp.mobile.data.sources.database.AppDatabase
import com.spotapp.mobile.data.sources.preferences.UserPreferencesManager
import com.spotapp.mobile.data.sources.preferences.impl.UserPreferencesManagerImpl
import com.spotapp.mobile.data.sources.remote.RestClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideAppDatabase(context: Context): AppDatabase = AppDatabase.get(context)

    @Provides
    fun provideRestClient(): RestClient = RestClient()

    @Provides
    fun provideUserPreferencesManager(context: Context): UserPreferencesManager =
        UserPreferencesManagerImpl(context)

    @Provides
    fun provideUsersRepository(appDatabase: AppDatabase): UsersRepository =
        UsersRepository(appDatabase.userDao())

}
