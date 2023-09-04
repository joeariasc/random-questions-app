package com.spotapp.mobile.domain.usecases

import com.spotapp.mobile.data.repository.UsersRepository
import com.spotapp.mobile.domain.model.Result
import com.spotapp.mobile.domain.model.asDomain
import com.spotapp.mobile.domain.model.user.User
import javax.inject.Inject

class AuthenticateNewAnonymousUser @Inject constructor(private val usersRepository: UsersRepository) {

    suspend operator fun invoke(): Result<User> =
        usersRepository.newAnonymousUser().asDomain()
}
