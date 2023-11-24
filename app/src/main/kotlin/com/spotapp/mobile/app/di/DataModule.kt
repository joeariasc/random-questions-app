package com.spotapp.mobile.app.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

    val firebaseAuth: FirebaseAuth = Firebase.auth

    val userPreferencesManager: UserPreferencesManager
        get() = UserPreferencesManagerImpl.getInstance(systemModule.applicationContext)

    val usersRepository: UsersRepository
        get() = UsersRepository(appDatabase.userDao(), firebaseAuth, userPreferencesManager)
}
