package com.spotapp.mobile.ui.feature.home

import com.spotapp.mobile.data.sources.database.users.UserDto

data class HomeViewModelState(
    val currentUserInformation: UserDto? = null,
    val isLoading: Boolean = false,
)