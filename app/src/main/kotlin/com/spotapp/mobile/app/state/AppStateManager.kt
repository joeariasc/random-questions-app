package com.spotapp.mobile.app.state

import com.spotapp.mobile.data.Result
import com.spotapp.mobile.data.repository.UsersRepository
import javax.inject.Inject

class AppStateManager @Inject constructor(
    private val usersRepository: UsersRepository,
) {
    suspend fun hasUserSignedOn(): Boolean {
        return (usersRepository.hasUserSignedOn() as? Result.Success)?.data ?: false
    }
}
