package com.spotapp.mobile.ui.feature.auth

data class ViewModelState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isAuthenticated: Boolean = false,
    val signInType: SignInType? = null,
    val email: String? = null,
    val password: String? = null,
) {

    fun toUiState(): UiState {
        if (isLoading) {
            return UiState.Loading
        }

        if (signInType == null) {
            return UiState.Idle()
        }

        return when (signInType) {
            SignInType.REGISTER ->
                UiState.Register(
                    isAuthenticated = isAuthenticated,
                    errorMessage = errorMessage,
                    email = email,
                    password = password,
                )

            SignInType.LOGIN ->
                UiState.Login(
                    isAuthenticated = isAuthenticated,
                    errorMessage = errorMessage,
                    email = email,
                    password = password
                )
        }
    }

    enum class SignInType {
        REGISTER, LOGIN
    }
}
