package com.spotapp.mobile.ui.feature.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotapp.mobile.domain.model.Result
import com.spotapp.mobile.domain.usecases.SignUpUserFirebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val registerUserFirebase: SignUpUserFirebase
) : ViewModel() {
    private val viewModelState: MutableStateFlow<SignUpViewModelState> =
        MutableStateFlow(SignUpViewModelState())

    val uiState = viewModelState.asStateFlow()

    fun onSignUp(fullName: String, email: String, password: String) {
        if (
            password.isBlank() ||
            password.length < 5 ||
            !isValidEmail(email) ||
            email.isBlank()
        ) {
            viewModelState.update {
                it.copy(errorMessage = "Email or Password invalid")
            }
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            registerUserFirebase(fullName, email, password).let { result ->
                when (result) {
                    is Result.Loading -> {
                        viewModelState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }

                    is Result.Error -> {
                        viewModelState.update {
                            it.copy(
                                errorMessage = result.exception.message,
                                isLoading = false
                            )
                        }
                    }

                    is Result.Success -> {
                        viewModelState.update {
                            it.copy(
                                isSuccessfullySignUp = true,
                                errorMessage = null,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun cleanError(){
        viewModelState.update { it.copy(errorMessage = null) }
    }

    private fun isValidEmail(email: String?): Boolean {
        val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
        return email?.matches(emailRegex) ?: false
    }
}