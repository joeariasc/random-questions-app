package com.spotapp.mobile.domain.usecases

import com.spotapp.mobile.data.repository.UsersRepository
import com.spotapp.mobile.domain.model.Result
import com.spotapp.mobile.domain.model.ext.asDomainResult
import com.spotapp.mobile.domain.model.user.User

class AuthenticateNewAnonymousUser(private val usersRepository: UsersRepository) {
}
