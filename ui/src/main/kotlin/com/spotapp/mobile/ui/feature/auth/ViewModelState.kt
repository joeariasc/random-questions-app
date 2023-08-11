package com.spotapp.mobile.ui.feature.auth

data class ViewModelState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isAuthenticated: Boolean = false,
    val signInType: SignInType? = null,
    val name: String? = null,
    val email: String? = null,
) {

    fun toUiState(): UiState {

        if (isLoading) {
            return UiState.Loading
        }

        if (signInType == null) {
            return UiState.Idle()
        }

        return when (signInType) {
            SignInType.ANONYMOUS ->
                UiState.Anonymous(
                    isAuthenticated = isAuthenticated,
                    errorMessage = errorMessage,
                )

            SignInType.EMAIL ->
                UiState.WithNameAndEmail(
                    isAuthenticated = isAuthenticated,
                    errorMessage = errorMessage,
                    name = name,
                    email = email,
                )
        }

    }

    enum class SignInType {
        ANONYMOUS, EMAIL
    }
}