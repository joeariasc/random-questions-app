package com.spotapp.mobile.ui.feature.auth

sealed interface UiState {

    val errorMessage: String?
    val isAuthenticated: Boolean

    data object Loading : UiState {
        override val errorMessage: String? = null
        override val isAuthenticated: Boolean = false
    }

    data class Idle(
        override val errorMessage: String? = null,
        override val isAuthenticated: Boolean = false
    ) : UiState

    data class Register(
        override val errorMessage: String? = null,
        override val isAuthenticated: Boolean = false,
        val email: String?,
        val password: String?,
    ) : UiState

    data class Login(
        override val errorMessage: String? = null,
        override val isAuthenticated: Boolean = false,
        val email: String?,
        val password: String?,
    ) : UiState
}
