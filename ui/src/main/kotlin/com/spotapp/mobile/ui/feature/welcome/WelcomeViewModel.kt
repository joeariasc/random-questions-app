package com.spotapp.mobile.ui.feature.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotapp.mobile.data.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val errorMessage: String? = null,
    val isSuccessfullySignIn: Boolean = false,
)

class WelcomeViewModel(private val usersRepository: UsersRepository) : ViewModel() {
    private val viewModelState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState())

    val uiState = viewModelState.asStateFlow()

    fun onSignIn() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                usersRepository.signInAnonymously()
            }.onSuccess {
                viewModelState.update {
                    it.copy(
                        isSuccessfullySignIn = true,
                        errorMessage = null,
                    )
                }
            }.onFailure { throwable ->
                viewModelState.update {
                    it.copy(
                        errorMessage = throwable.message,
                    )
                }
            }
        }
    }

    fun cleanError() {
        viewModelState.update { it.copy(errorMessage = null) }
    }
}
