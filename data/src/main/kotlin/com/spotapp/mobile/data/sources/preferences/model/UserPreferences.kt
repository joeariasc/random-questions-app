package com.spotapp.mobile.data.sources.preferences.model

data class UserPreferences(
    val sessionStatus: SessionState,
    val userName: String?,
    val userEmail: String?,
)

enum class SessionState {
    REGISTERED, LOGGED_IN, UNREGISTERED
}
