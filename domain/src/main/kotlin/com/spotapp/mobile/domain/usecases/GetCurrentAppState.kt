package com.spotapp.mobile.domain.usecases

import com.spotapp.mobile.data.repository.UsersRepository

class GetCurrentAppState(private val usersRepository: UsersRepository) {

    operator fun invoke(): Boolean = usersRepository.hasUserSignedOn()
}
