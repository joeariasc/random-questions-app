package com.spotapp.mobile.app.di

import com.spotapp.mobile.domain.usecases.AuthenticateNewAnonymousUser
import com.spotapp.mobile.domain.usecases.AuthenticateNewUserWithEmail
import com.spotapp.mobile.domain.usecases.GetCurrentAppState

class DomainModule(private val dataModule: DataModule) {

    val authenticateNewAnonymousUserUseCase: AuthenticateNewAnonymousUser
        get() = AuthenticateNewAnonymousUser(dataModule.usersRepository)

    val authenticateNewUserWithEmailUseCase: AuthenticateNewUserWithEmail
        get() = AuthenticateNewUserWithEmail(dataModule.usersRepository)

    val getCurrentAppStateUseCase: GetCurrentAppState
        get() = GetCurrentAppState(dataModule.usersRepository)
}
