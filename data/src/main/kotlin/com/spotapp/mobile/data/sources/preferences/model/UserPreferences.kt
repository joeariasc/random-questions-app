package com.spotapp.mobile.data.sources.preferences.model

data class UserPreferences(
    val sessionStatus: SessionState,
    val userEmail: String?,
    val userPassword: String?,
    val rememberCredentials: Boolean = false,
)

enum class SessionState {
    REGISTERED, LOGGED_IN, UNREGISTERED
}
