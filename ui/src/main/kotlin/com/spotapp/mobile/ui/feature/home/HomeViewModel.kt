package com.spotapp.mobile.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotapp.mobile.data.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val usersRepository: UsersRepository
) : ViewModel() {
    private val viewModelState: MutableStateFlow<HomeViewModelState> =
        MutableStateFlow(HomeViewModelState())

    val uiState = viewModelState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            viewModelState.update { it.copy(currentUserInformation = usersRepository.getUserInformation()) }
        }
    }
}
