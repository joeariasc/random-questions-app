package com.spotapp.mobile.domain.usecases

import com.spotapp.mobile.data.repository.UsersRepository
import com.spotapp.mobile.domain.model.Result
import com.spotapp.mobile.domain.model.ext.asUiResult
import com.spotapp.mobile.domain.model.user.User

class SignInUserFirebase(private val usersRepository: UsersRepository) {
    suspend operator fun invoke(email: String, password: String): Result<User> =
        usersRepository.signInUserFirebase(email, password).asUiResult()
}