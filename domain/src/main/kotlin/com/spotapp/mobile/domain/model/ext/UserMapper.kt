package com.spotapp.mobile.domain.model.ext

import com.google.firebase.auth.AuthResult
import com.spotapp.mobile.data.sources.database.users.UserDto
import com.spotapp.mobile.domain.model.user.User
import com.spotapp.mobile.data.Result as DataResult
import com.spotapp.mobile.domain.model.Result as DomainResult
import com.spotapp.mobile.data.DataResult as FirebaseResult

fun DataResult<UserDto>.asDomainResult(): DomainResult<User> =
    asDomainResult { userDto -> userDto.asUser() }

private fun UserDto.asUser(): User =
    User(userId = id, name = userInfo?.name, email = userInfo?.email)


fun FirebaseResult<AuthResult>.asUiResult(): DomainResult<User> {
    return if (error != null) DomainResult.Error(exception = error!!) else DomainResult.Success(
        data = User(
            userId = 0, name = data?.user?.displayName, email = data?.user?.email
        )
    )
}

