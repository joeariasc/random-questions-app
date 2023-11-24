package com.spotapp.mobile.data.sources.preferences.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.spotapp.mobile.data.sources.preferences.PreferencesKeys
import com.spotapp.mobile.data.sources.preferences.UserPreferencesManager
import com.spotapp.mobile.data.sources.preferences.model.SessionState
import com.spotapp.mobile.data.sources.preferences.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesManagerImpl(context: Context) : UserPreferencesManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        "settings"
    )

    private val dataStore: DataStore<Preferences> = context.dataStore

    override val userPreferences: Flow<UserPreferences> = dataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        val sessionStatus =
            preferences[PreferencesKeys.sessionStatus] ?: SessionState.UNREGISTERED.name
        val userName = preferences[PreferencesKeys.userName]
        val userEmail = preferences[PreferencesKeys.userEmail]

        UserPreferences(
            sessionStatus = SessionState.valueOf(sessionStatus),
            userName = userName,
            userEmail = userEmail,
        )
    }

    override suspend fun persist(transform: suspend (MutablePreferences) -> Unit): Preferences =
        dataStore.edit(transform)

    companion object {
        @Volatile
        private var instance: UserPreferencesManagerImpl? = null

        fun getInstance(context: Context): UserPreferencesManagerImpl {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = UserPreferencesManagerImpl(context)
                    }
                }
            }
            return instance!!
        }
    }
}
