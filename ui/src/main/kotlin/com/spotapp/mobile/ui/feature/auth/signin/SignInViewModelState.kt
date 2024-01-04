package com.spotapp.mobile.ui.feature.auth.signin

data class SignInViewModelState(
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isSuccessfullySignIn: Boolean = false,
)