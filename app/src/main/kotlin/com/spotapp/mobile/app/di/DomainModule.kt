package com.spotapp.mobile.app.di

import com.spotapp.mobile.domain.usecases.AuthenticateNewUserWithEmail
import com.spotapp.mobile.domain.usecases.RegisterUserFirebase
import com.spotapp.mobile.domain.usecases.GetCurrentAppState
import com.spotapp.mobile.domain.usecases.SignInUserFirebase

class DomainModule(private val dataModule: DataModule) {

    val authenticateNewUserWithEmailUseCase: AuthenticateNewUserWithEmail
        get() = AuthenticateNewUserWithEmail(dataModule.usersRepository)

    val getCurrentAppStateUseCase: GetCurrentAppState
        get() = GetCurrentAppState(dataModule.usersRepository)

    val authenticateUserFirebase: RegisterUserFirebase
        get() = RegisterUserFirebase(dataModule.usersRepository)

    val signInUserFirebase: SignInUserFirebase
        get() = SignInUserFirebase(dataModule.usersRepository)
}
