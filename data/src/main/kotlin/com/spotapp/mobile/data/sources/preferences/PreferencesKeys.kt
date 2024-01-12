package com.spotapp.mobile.data.sources.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val sessionStatus = stringPreferencesKey("SESSION_STATUS")
    val rememberCredentials = booleanPreferencesKey("REMEMBER_CREDENTIALS")
    val userEmail = stringPreferencesKey("USER_EMAIL")
    val userPassword = stringPreferencesKey("USER_PASSWORD")
}