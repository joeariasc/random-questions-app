package com.spotapp.mobile.ui.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotapp.mobile.data.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val usersRepository: UsersRepository
) : ViewModel() {
    private val viewModelState: MutableStateFlow<SettingsViewModelState> =
        MutableStateFlow(SettingsViewModelState())

    val uiState = viewModelState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            viewModelState.update {
                it.copy(
                    userDto = usersRepository.getUserInformation()
                )
            }
        }
    }

    fun signOut() {
        runCatching {
            usersRepository.signOutFirebase()
        }.onFailure {
            viewModelState.update { it.copy(errorMessage = it.errorMessage) }
        }.onSuccess {
            viewModelState.update { it.copy(isLoggedOut = true) }
        }
    }

    fun cleanError() {
        viewModelState.update { it.copy(errorMessage = null) }
    }
}