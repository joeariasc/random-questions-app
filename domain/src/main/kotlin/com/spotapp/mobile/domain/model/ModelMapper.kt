package com.spotapp.mobile.domain.model

import com.spotapp.mobile.data.sources.database.users.UserDto
import com.spotapp.mobile.domain.model.user.User

fun UserDto.asUser(): User =
    User(userId = id, name = userInfo?.name, email = userInfo?.email)

fun com.spotapp.mobile.data.Result<UserDto>.asDomain(): Result<User> =
    when (this) {
        is com.spotapp.mobile.data.Result.Error -> Result.Error(exception)
        is com.spotapp.mobile.data.Result.Loading -> Result.Loading
        is com.spotapp.mobile.data.Result.Success -> Result.Success(data.asUser())
    }
