package com.spotapp.mobile.ui.feature.home

data class HomeViewModelState(
    val currentUserInformation: Pair<String?, String?> = Pair(null, null),
    val isLoading: Boolean = false,
)