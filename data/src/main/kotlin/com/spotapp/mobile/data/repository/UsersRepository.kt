package com.spotapp.mobile.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.spotapp.mobile.data.DataResult
import com.spotapp.mobile.data.Result
import com.spotapp.mobile.data.mapper.withLocalSource
import com.spotapp.mobile.data.sources.database.users.UserDao
import com.spotapp.mobile.data.sources.database.users.UserDto
import com.spotapp.mobile.data.sources.database.users.UserInfoDto
import com.spotapp.mobile.data.sources.preferences.PreferencesKeys
import com.spotapp.mobile.data.sources.preferences.UserPreferencesManager
import com.spotapp.mobile.data.sources.preferences.model.SessionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UsersRepository(
    private val userDao: UserDao,
    private val auth: FirebaseAuth,
    private val userPreferences: UserPreferencesManager
) {

    suspend fun newAnonymousUser(): Result<UserDto> =
        withLocalSource { UserDto().also { userDao.save(it) } }

    suspend fun newUserWith(
        name: String,
        email: String
    ): Result<UserDto> = withLocalSource {
        UserDto(
            userInfo = UserInfoDto(
                name = name,
                email = email
            )
        ).also {
            userDao.save(it)
        }
    }

    suspend fun signInUserFirebase(email: String, password: String): DataResult<AuthResult> =
        withContext(Dispatchers.IO) {
            try {
                val authResult = auth.signInWithEmailAndPassword(email, password).await()
                userPreferences.persist {
                    it[PreferencesKeys.sessionStatus] = SessionState.LOGGED_IN.name
                    it[PreferencesKeys.userName] = authResult?.user?.displayName ?: ""
                    it[PreferencesKeys.userEmail] = authResult?.user?.email ?: ""
                }
                DataResult(data = authResult)
            } catch (e: Exception) {
                DataResult(error = e)
            }
        }

    suspend fun registerUserFirebase(email: String, password: String): DataResult<AuthResult> =
        withContext(Dispatchers.IO) {
            try {
                val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                userPreferences.persist {
                    it[PreferencesKeys.sessionStatus] = SessionState.REGISTERED.name
                    it[PreferencesKeys.userName] = authResult?.user?.displayName ?: ""
                    it[PreferencesKeys.userEmail] = authResult?.user?.email ?: ""
                }
                DataResult(data = authResult)
            } catch (e: Exception) {
                DataResult(error = e)
            }
        }

    suspend fun hasUserSignedOn(): DataResult<Boolean> =
        withContext(Dispatchers.IO) {
            DataResult(data = userPreferences.userPreferences.first().sessionStatus != SessionState.UNREGISTERED)
        }
}
