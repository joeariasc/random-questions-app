package com.spotapp.mobile.ui.feature.auth.signup

data class SignUpViewModelState(
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isSuccessfullySignUp: Boolean = false,
)