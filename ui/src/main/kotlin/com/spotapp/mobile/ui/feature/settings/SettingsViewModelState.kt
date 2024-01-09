package com.spotapp.mobile.ui.feature.settings

import com.spotapp.mobile.data.sources.database.users.UserDto

data class SettingsViewModelState(
    val userDto: UserDto? = null,
    val errorMessage: String? = null,
    val isLoggedOut: Boolean = false,
)