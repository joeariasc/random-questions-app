package com.spotapp.mobile.domain.usecases

import com.spotapp.mobile.data.Result
import com.spotapp.mobile.data.repository.UsersRepository
import com.spotapp.mobile.domain.model.state.AppState

class GetCurrentAppState(private val usersRepository: UsersRepository) {

    suspend operator fun invoke(): AppState {
        val hasUserSignedOn = (usersRepository.hasUserSignedOn() as? Result.Success)?.data ?: false
        return if (hasUserSignedOn) AppState.SIGNED_ON else AppState.SIGNED_OFF
    }
}
