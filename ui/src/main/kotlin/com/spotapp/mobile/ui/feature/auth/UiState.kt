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
        override val isAuthenticated: Boolean = false,
    ) : UiState

    data class Anonymous(
        override val errorMessage: String? = null,
        override val isAuthenticated: Boolean = false,
    ) : UiState

    data class WithNameAndEmail(
        override val errorMessage: String? = null,
        override val isAuthenticated: Boolean = false,
        val name: String?,
        val email: String?,
    ) : UiState

}