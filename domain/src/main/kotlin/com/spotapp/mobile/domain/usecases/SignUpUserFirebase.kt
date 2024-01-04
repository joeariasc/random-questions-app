package com.spotapp.mobile.domain.usecases

import com.spotapp.mobile.data.repository.UsersRepository
import com.spotapp.mobile.domain.model.Result
import com.spotapp.mobile.domain.model.ext.asUiResult
import com.spotapp.mobile.domain.model.user.User

class SignUpUserFirebase(private val usersRepository: UsersRepository) {
    suspend operator fun invoke(fullName: String, email: String, password: String): Result<User> =
        usersRepository.signUpUserFirebase(fullName = fullName, email = email, password = password)
            .asUiResult()
}