package com.spotapp.mobile.domain.usecases

import com.spotapp.mobile.data.repository.UsersRepository

class GetUserInformation(private val usersRepository: UsersRepository) {
    suspend operator fun invoke(): Pair<String?, String?> =
        usersRepository.getUserInformation()
}