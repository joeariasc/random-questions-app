package com.spotapp.mobile.ui.feature.settings

import com.spotapp.mobile.data.models.User

data class SettingsViewModelState(
    val user: User? = null,
    val errorMessage: String? = null,
    val isLoggedOut: Boolean = false,
)