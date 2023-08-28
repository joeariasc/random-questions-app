package com.spotapp.mobile.domain.usecases

import com.spotapp.mobile.data.repository.UsersRepository
import com.spotapp.mobile.domain.model.Result
import com.spotapp.mobile.domain.model.asDomain
import com.spotapp.mobile.domain.model.user.User

class AuthenticateNewUserWithEmail(private val usersRepository: UsersRepository) {

    suspend operator fun invoke(
        name: String,
        email: String
    ): Result<User> = usersRepository.newUserWith(name, email).asDomain()
}
