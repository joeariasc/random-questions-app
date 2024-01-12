package com.spotapp.mobile.ui.feature.auth.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotapp.mobile.data.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(private val usersRepository: UsersRepository) : ViewModel() {
    private val viewModelState: MutableStateFlow<SignInViewModelState> =
        MutableStateFlow(SignInViewModelState())

    val uiState = viewModelState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            usersRepository.getUserCredentials()
                .let { (rememberCredentials, userEmail, userPassword) ->
                    Log.d(
                        "signInUserFirebase",
                        "userEmail => $userEmail, userPassword => $userPassword, remember credentials => $rememberCredentials"
                    )
                    if (rememberCredentials) {
                        viewModelState.update {
                            it.copy(
                                userEmail = userEmail,
                                userPassword = userPassword,
                            )
                        }
                    }
                    viewModelState.update { it.copy(rememberCredentials = rememberCredentials) }
                }
        }
    }

    fun onSignIn() {
        val state = viewModelState.value
        if (
            state.userPassword.isNullOrBlank() ||
            state.userPassword.length < 5 ||
            !isValidEmail(state.userEmail) ||
            state.userEmail.isNullOrBlank()
        ) {
            viewModelState.update {
                it.copy(errorMessage = "Email or Password invalid")
            }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                usersRepository.signInUserFirebase(
                    state.userEmail,
                    state.userPassword,
                    state.rememberCredentials
                )
            }.onSuccess {
                viewModelState.update {
                    it.copy(
                        isSuccessfullySignIn = true,
                        errorMessage = null,
                        isLoading = false
                    )
                }
            }.onFailure { throwable ->
                viewModelState.update {
                    it.copy(
                        errorMessage = throwable.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun setUserEmail(email: String) {
        viewModelState.update { it.copy(userEmail = email) }
    }

    fun setUserPassword(password: String) {
        viewModelState.update { it.copy(userPassword = password) }
    }

    fun setRememberCredentials(remember: Boolean) {
        viewModelState.update { it.copy(rememberCredentials = remember) }
    }

    fun cleanError() {
        viewModelState.update { it.copy(errorMessage = null) }
    }

    private fun isValidEmail(email: String?): Boolean {
        val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
        return email?.matches(emailRegex) ?: false
    }
}