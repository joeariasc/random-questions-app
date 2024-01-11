package com.spotapp.mobile.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
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
    ) {
        return withContext(coroutineDispatcher) {
            runCatching {
                auth.createUserWithEmailAndPassword(email, password).await()
            }.fold(
                onSuccess = { authResult ->
                    authResult.user?.getIdToken(true)?.await()?.let {
                        Log.d("GetTokenResult", "token => ${it.token}")
                    }
                    syncUserInfo(email, fullName)
                }, onFailure = {
                    throw it
                }
            )
        }
    }

    suspend fun signInUserFirebase(email: String, password: String) {
        return withContext(Dispatchers.IO) {
            runCatching {
                auth.signInWithEmailAndPassword(email, password).await()
            }.fold(
                onSuccess = { authResult ->
                    syncUserInfo(email)
                    userPreferencesManager.persist {
                        it[PreferencesKeys.sessionStatus] = SessionState.LOGGED_IN.name
                    }
                    authResult.user?.getIdToken(true)?.await()?.let {
                        Log.d("GetTokenResult", "token => ${it.token}")
                    }
                    // TODO: Implement firestore synchronization
                }, onFailure = {
                    throw it
                }
            )
        }
    }

    fun signOutFirebase() {
        runCatching {
            auth.signOut()
        }.fold(
            onSuccess = {
                Log.d("signOutFirebase", "signOut successful!")
            }, onFailure = {
                throw it
            }
        )
    }

    suspend fun getUserInformation(): UserDto {
        val preferences = userPreferencesManager.userPreferences.first()
        return userDao.findByEmail(preferences.userEmail ?: "")
    }

    private suspend fun syncUserInfo(email: String, fullName: String? = null) {
        if (fullName == null) {
            // Sync user information
        }

        UserDto(
            userInfo = UserInfoDto(
                email = email,
                name = fullName ?: "",
            )
        ).also {
            userDao.save(it)
        }

        userPreferencesManager.persist {
            it[PreferencesKeys.sessionStatus] = SessionState.REGISTERED.name
            it[PreferencesKeys.userEmail] = email
        }
    }


    fun hasUserSignedOn(): Boolean = auth.currentUser != null
}
