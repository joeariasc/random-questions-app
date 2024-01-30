package com.spotapp.mobile.app.di

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.spotapp.mobile.data.repository.GameRepository
import com.spotapp.mobile.data.repository.UsersRepository
import com.spotapp.mobile.data.sources.database.AppDatabase
import com.spotapp.mobile.data.sources.preferences.UserPreferencesManager
import com.spotapp.mobile.data.sources.preferences.impl.UserPreferencesManagerImpl
import com.spotapp.mobile.data.sources.remote.RestClient
import com.spotapp.mobile.data.sources.remote.firebasedatabase.FDatabaseService
import com.spotapp.mobile.data.sources.remote.firebasedatabase.model.UserData
import com.spotapp.mobile.data.sources.remote.firestore.FirestoreService
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class DataModule(private val systemModule: SystemModule) {

    val appDatabase: AppDatabase
        get() = AppDatabase.get(systemModule.applicationContext)

    val restClient: RestClient
        get() = RestClient()

    private val firebaseAuth: FirebaseAuth = Firebase.auth

    val firestoreService: FirestoreService = FirestoreService(firebaseAuth)

    val firebaseDBService: FDatabaseService = FDatabaseService(firebaseAuth)

    val rankinUpdate: Flow<List<UserData>> = firebaseDBService.subscribeToRankingUpdates

    private val userPreferencesManager: UserPreferencesManager
        get() = UserPreferencesManagerImpl.getInstance(systemModule.applicationContext)

    val usersRepository: UsersRepository
        get() = UsersRepository(
            auth = firebaseAuth,
            userPreferencesManager = userPreferencesManager,
            firestoreService = firestoreService
        )

    val gameRepository: GameRepository
        get() = GameRepository(firestoreService, firebaseDBService)

}
