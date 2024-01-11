package com.spotapp.mobile.app.di

import com.spotapp.mobile.domain.usecases.GetCurrentAppState

class DomainModule(private val dataModule: DataModule) {

    val getCurrentAppStateUseCase: GetCurrentAppState
        get() = GetCurrentAppState(dataModule.usersRepository)
}
