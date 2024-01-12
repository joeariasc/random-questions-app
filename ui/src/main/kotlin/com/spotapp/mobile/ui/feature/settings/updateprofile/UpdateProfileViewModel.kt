package com.spotapp.mobile.ui.feature.settings.updateprofile

import android.util.Log
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
    val userName: String? = null,
    val userEmail: String? = null,
    val showConfirmationModal: Boolean = false,
    val showEditPasswordDialog: Boolean = false,
)

class UpdateProfileViewModel(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val viewModelState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState())

    val uiState = viewModelState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            usersRepository.getUserInformation()?.let { user ->
                Log.d("UpdateProfileViewModel", "user => $user")
                viewModelState.update {
                    it.copy(
                        userName = user.name,
                        userEmail = user.email,
                    )
                }
            }
        }
    }

    fun updateUserName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                usersRepository.updateUserName(name)
            }.onSuccess {
                viewModelState.update {
                    it.copy(
                        showConfirmationModal = true
                    )
                }
            }.onFailure { throwable ->
                viewModelState.update {
                    it.copy(
                        errorMessage = throwable.message
                    )
                }
            }
        }
    }

    fun showUpdatePasswordDialog() {
        viewModelState.update {
            it.copy(
                showEditPasswordDialog = true,
            )
        }
    }

    fun hideUpdatePasswordDialog() {
        viewModelState.update {
            it.copy(
                showEditPasswordDialog = false,
            )
        }
    }

    fun updateUserPassword(oldPassword: String, newPassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                usersRepository.updateUserPassword(oldPassword, newPassword)
            }.onSuccess {
                viewModelState.update {
                    it.copy(
                        showConfirmationModal = true,
                        showEditPasswordDialog = false,
                    )
                }
            }.onFailure { throwable ->
                viewModelState.update {
                    it.copy(
                        errorMessage = throwable.message,
                        showEditPasswordDialog = false,
                    )
                }
            }
        }
    }

    fun hideConfirmationModal() {
        viewModelState.update { it.copy(showConfirmationModal = false) }
    }

    fun cleanError() {
        viewModelState.update { it.copy(errorMessage = null) }
    }
}