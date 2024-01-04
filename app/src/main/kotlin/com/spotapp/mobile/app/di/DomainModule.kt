package com.spotapp.mobile.app.di

import com.spotapp.mobile.domain.usecases.AuthenticateNewUserWithEmail
import com.spotapp.mobile.domain.usecases.SignUpUserFirebase
import com.spotapp.mobile.domain.usecases.GetCurrentAppState
import com.spotapp.mobile.domain.usecases.GetUserInformation
import com.spotapp.mobile.domain.usecases.SignInUserFirebase

class DomainModule(private val dataModule: DataModule) {

    val authenticateNewUserWithEmailUseCase: AuthenticateNewUserWithEmail
        get() = AuthenticateNewUserWithEmail(dataModule.usersRepository)

    val getCurrentAppStateUseCase: GetCurrentAppState
        get() = GetCurrentAppState(dataModule.usersRepository)

    val signUpUserFirebase: SignUpUserFirebase
        get() = SignUpUserFirebase(dataModule.usersRepository)

    val signInUserFirebase: SignInUserFirebase
        get() = SignInUserFirebase(dataModule.usersRepository)

    val getUserInformation: GetUserInformation
        get() = GetUserInformation(dataModule.usersRepository)
}
