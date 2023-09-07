package com.spotapp.mobile.domain.model.ext

import com.spotapp.mobile.data.sources.database.users.UserDto
import com.spotapp.mobile.domain.model.user.User
import com.spotapp.mobile.data.Result as DataResult
import com.spotapp.mobile.domain.model.Result as DomainResult

fun DataResult<UserDto>.asDomainResult(): DomainResult<User> =
    asDomainResult { userDto -> userDto.asUser() }

private fun UserDto.asUser(): User =
    User(userId = id, name = userInfo?.name, email = userInfo?.email)
