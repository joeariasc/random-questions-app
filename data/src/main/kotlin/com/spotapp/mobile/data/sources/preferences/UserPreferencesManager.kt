package com.spotapp.mobile.data.sources.preferences

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import com.spotapp.mobile.data.sources.preferences.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesManager {

    val userPreferences: Flow<UserPreferences>

    suspend fun persist(transform: suspend (MutablePreferences) -> Unit): Preferences
}
