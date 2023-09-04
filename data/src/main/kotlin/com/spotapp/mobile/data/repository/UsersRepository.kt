package com.spotapp.mobile.data.repository

import com.spotapp.mobile.data.Result
import com.spotapp.mobile.data.mapper.withLocalSource
import com.spotapp.mobile.data.sources.database.users.UserDao
import com.spotapp.mobile.data.sources.database.users.UserDto
import com.spotapp.mobile.data.sources.database.users.UserInfoDto

class UsersRepository(private val userDao: UserDao) {

    suspend fun newAnonymousUser(): Result<UserDto> =
        withLocalSource { UserDto().also { userDao.save(it) } }

    suspend fun newUserWith(
        name: String, email: String
    ): Result<UserDto> = withLocalSource {
        UserDto(
            userInfo = UserInfoDto(
                name = name, email = email
            )
        ).also {
            userDao.save(it)
        }
    }

    suspend fun hasUserSignedOn(): Result<Boolean> =
        withLocalSource { userDao.findAll().isNotEmpty() }
}