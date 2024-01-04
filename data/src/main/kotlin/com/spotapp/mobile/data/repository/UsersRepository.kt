package com.spotapp.mobile.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.spotapp.mobile.data.DataResult
import com.spotapp.mobile.data.sources.database.users.UserDao
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
    ): Result<AuthResult> {
        return withContext(coroutineDispatcher) {
            runCatching {
                auth.createUserWithEmailAndPassword(email, password).await()
            }.onSuccess { authResult ->
                userPreferencesManager.persist {
                    it[PreferencesKeys.sessionStatus] = SessionState.REGISTERED.name
                    it[PreferencesKeys.userName] = fullName
                    it[PreferencesKeys.userEmail] = authResult?.user?.email ?: email
                }
                DataResult(
                    data = authResult
                )
            }.onFailure { throwable ->
                DataResult<AuthResult>(error = throwable.message)
            }
        }
    }

    suspend fun signInUserFirebase(email: String, password: String): Result<AuthResult> {
        return withContext(Dispatchers.IO) {
            runCatching {
                auth.signInWithEmailAndPassword(email, password).await()
            }.onSuccess { authResult ->
                userPreferencesManager.persist {
                    it[PreferencesKeys.sessionStatus] = SessionState.LOGGED_IN.name
                }
                DataResult(
                    data = authResult
                )
            }.onFailure { throwable ->
                DataResult<AuthResult>(error = throwable.message)
            }
        }
    }

    suspend fun signOutFirebase() {
        auth.signOut()
    }

    suspend fun getUserInformation(): Pair<String?, String?> {
        val preferences = userPreferencesManager.userPreferences.first()
        return Pair(auth.currentUser?.displayName, auth.currentUser?.email)
    }


    suspend fun hasUserSignedOn(): DataResult<Boolean> =
        withContext(Dispatchers.IO) {
            DataResult(data = auth.currentUser != null)
        }
}
