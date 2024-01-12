package com.spotapp.mobile.ui.feature.auth.signin

data class SignInViewModelState(
    val userCredentials: Pair<String?, String?> = Pair(null, null),
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isSuccessfullySignIn: Boolean = false,
    val userEmail: String? = null,
    val userPassword: String? = null,
    val rememberCredentials: Boolean = false,
)