package com.spotapp.mobile.data.sources.preferences

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val sessionStatus = stringPreferencesKey("SESSION_STATUS")
    val userName = stringPreferencesKey("USER_NAME")
    val userEmail = stringPreferencesKey("USER_EMAIL")
}