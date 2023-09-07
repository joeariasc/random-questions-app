package com.spotapp.mobile.ui.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotapp.mobile.domain.model.Result
import com.spotapp.mobile.domain.model.user.User
import com.spotapp.mobile.domain.usecases.AuthenticateNewAnonymousUser
import com.spotapp.mobile.domain.usecases.AuthenticateNewUserWithEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authenticateNewAnonymousUser: AuthenticateNewAnonymousUser,
    private val authenticateNewUserWithEmail: AuthenticateNewUserWithEmail
) : ViewModel() {

    private val viewModelState: MutableStateFlow<ViewModelState> =
        MutableStateFlow(ViewModelState())
    val uiState = viewModelState.map(ViewModelState::toUiState).stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        viewModelState.value.toUiState()
    )

    fun onAnonymousSignInClick() {
        viewModelState.update {
            it.copy(signInType = ViewModelState.SignInType.ANONYMOUS)
        }.also {
            authenticateNewUser {
                authenticateNewAnonymousUser()
            }
        }
    }

    fun onUsingNameAndEmailSignInClick() {
        viewModelState.update {
            it.copy(signInType = ViewModelState.SignInType.EMAIL)
        }
    }

    fun onEmailChange(text: String) {
        viewModelState.update {
            it.copy(email = text)
        }
    }

    fun onNameChange(text: String) {
        viewModelState.update {
            it.copy(name = text)
        }
    }

    fun onSignInWithNameAndEmailClick() {
        val name = viewModelState.value.name
        val email = viewModelState.value.email

        if (name.isNullOrBlank() || email.isNullOrBlank()) {
            viewModelState.update {
                it.copy(
                    errorMessage = "Name or email invalid"
                )
            }
            return
        }

        authenticateNewUser {
            authenticateNewUserWithEmail(name, email)
        }
    }

    private fun authenticateNewUser(authenticate: suspend () -> Result<User>) =
        viewModelScope.launch {
            when (authenticate()) {
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
                            errorMessage = it.errorMessage,
                            isLoading = false
                        )
                    }
                }

                is Result.Success -> {
                    viewModelState.update {
                        it.copy(
                            isAuthenticated = true,
                            errorMessage = null,
                            isLoading = false
                        )
                    }
                }
            }
        }
}
