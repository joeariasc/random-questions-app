package com.spotapp.mobile.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.spotapp.mobile.data.DataResult
import com.spotapp.mobile.data.sources.database.users.UserDao
import com.spotapp.mobile.data.sources.database.users.UserDto
import com.spotapp.mobile.data.sources.database.users.UserInfoDto
import com.spotapp.mobile.data.sources.preferences.PreferencesKeys
import com.spotapp.mobile.data.sources.preferences.UserPreferencesManager
import com.spotapp.mobile.data.sources.preferences.model.SessionState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UsersRepository(
    private val userDao: UserDao,
    private val auth: FirebaseAuth,
    private val userPreferencesManager: UserPreferencesManager,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend fun signUpUserFirebase(
        fullName: String,
        email: String,
        password: String
    ): UserDto {
        return withContext(coroutineDispatcher) {
            runCatching {
                auth.createUserWithEmailAndPassword(email, password).await()
            }.fold(
                onSuccess = { _ ->
                    syncUserInfo(email, fullName)
                    getUserInformation()
                }, onFailure = {
                    throw it
                }
            )
        }
    }

    suspend fun signInUserFirebase(email: String, password: String): UserDto {
        return withContext(Dispatchers.IO) {
            runCatching {
                auth.signInWithEmailAndPassword(email, password).await()
            }.fold(
                onSuccess = { _ ->
                    userPreferencesManager.persist {
                        it[PreferencesKeys.sessionStatus] = SessionState.LOGGED_IN.name
                    }
                    // TODO: Implement firestore synchronization
                    getUserInformation()
                }, onFailure = {
                    throw it
                }
            )
        }
    }

    suspend fun signOutFirebase() {
        auth.signOut()
    }

    suspend fun getUserInformation(): UserDto {
        val preferences = userPreferencesManager.userPreferences.first()
        return userDao.findByEmail(preferences.userEmail ?: "")
    }

    private suspend fun syncUserInfo(email: String, fullName: String) {
        getUserInformation().let {
            if (it.userInfo == null) {
                // Sync user information
            }
        }
        UserDto(
            userInfo = UserInfoDto(
                email = email,
                name = fullName,
            )
        ).also {
            userDao.save(it)
        }
        userPreferencesManager.persist {
            it[PreferencesKeys.sessionStatus] = SessionState.REGISTERED.name
            it[PreferencesKeys.userEmail] = email
        }
    }


    suspend fun hasUserSignedOn(): DataResult<Boolean> =
        withContext(Dispatchers.IO) {
            DataResult(data = auth.currentUser != null)
        }
}
