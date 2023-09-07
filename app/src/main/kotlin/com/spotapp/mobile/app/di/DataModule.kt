package com.spotapp.mobile.app.di

import com.spotapp.mobile.data.repository.UsersRepository
import com.spotapp.mobile.data.sources.database.AppDatabase
import com.spotapp.mobile.data.sources.preferences.UserPreferencesManager
import com.spotapp.mobile.data.sources.preferences.impl.UserPreferencesManagerImpl
import com.spotapp.mobile.data.sources.remote.RestClient

class DataModule(private val systemModule: SystemModule) {

    val appDatabase: AppDatabase
        get() = AppDatabase.get(systemModule.applicationContext)

    val restClient: RestClient
        get() = RestClient()

    val userPreferencesManager: UserPreferencesManager
        get() = UserPreferencesManagerImpl(systemModule.applicationContext)

    val usersRepository: UsersRepository
        get() = UsersRepository(appDatabase.userDao())
}
