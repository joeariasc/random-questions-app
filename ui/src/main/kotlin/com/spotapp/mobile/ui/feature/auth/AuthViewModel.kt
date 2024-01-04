package com.spotapp.mobile.ui.feature.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotapp.mobile.domain.model.Result
import com.spotapp.mobile.domain.model.user.User
import com.spotapp.mobile.domain.usecases.SignUpUserFirebase
import com.spotapp.mobile.domain.usecases.SignInUserFirebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val registerUserFirebase: SignUpUserFirebase,
    private val signInUserFirebase: SignInUserFirebase,
) : ViewModel() {

    private val viewModelState: MutableStateFlow<ViewModelState> =
        MutableStateFlow(ViewModelState())
    val uiState = viewModelState.map(ViewModelState::toUiState).stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        viewModelState.value.toUiState()
    )

    fun registerNewUserClick() {
        viewModelState.update {
            it.copy(signInType = ViewModelState.SignInType.REGISTER)
        }
    }

    fun logInClick() {
        viewModelState.update {
            it.copy(signInType = ViewModelState.SignInType.LOGIN)
        }
    }

    fun onEmailChange(text: String) {
        viewModelState.update {
            it.copy(email = text, errorMessage = null)
        }
    }

    fun onPasswordChange(text: String) {
        viewModelState.update {
            it.copy(password = text, errorMessage = null)
        }
    }

    fun onLogInUser() {
        val email = viewModelState.value.email
        val password = viewModelState.value.password

        if (password.isNullOrBlank() || email.isNullOrBlank()) {
            viewModelState.update {
                it.copy(
                    errorMessage = "Name or email invalid"
                )
            }
            return
        }
    }

    fun onRegisterUser() {
        val email = viewModelState.value.email
        val password = viewModelState.value.password

        if (
            password.isNullOrBlank() ||
            password.length < 5 ||
            !isValidEmail(email) ||
            email.isNullOrBlank()
        ) {
            viewModelState.update {
                it.copy(
                    errorMessage = "Email or Password invalid"
                )
            }
            return
        }
    }

    private fun authenticateUser(authenticate: suspend () -> Result<User>) {
        viewModelScope.launch {
            authenticate().let { result ->
                Log.d("FIREBASE_AUTH", "Function called!")
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
                                isAuthenticated = false,
                                errorMessage = null,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }


    private fun isValidEmail(email: String?): Boolean {
        val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
        return email?.matches(emailRegex) ?: false
    }
}
