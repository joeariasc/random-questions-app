package com.spotapp.mobile.data.repository.impl

import android.database.sqlite.SQLiteException
import com.spotapp.mobile.data.Result
import com.spotapp.mobile.data.sources.database.users.UserDao
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UsersRepositoryImplTest {

    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        userDao = mockk()
    }

    @Test
    fun newAnonymousUserWithNoError() {

        coJustRun { userDao.save(any()) }

        val repositoryImpl = UsersRepositoryImpl(userDao)

        runTest {
            assertTrue(repositoryImpl.newAnonymousUser() is Result.Success)
        }
    }

    @Test
    fun newAnonymousUserWithError() {

        val sqliteException = SQLiteException("an SQLite exception was thrown")
        coEvery { userDao.save(any()) } throws sqliteException

        val repositoryImpl = UsersRepositoryImpl(userDao)

        runTest {
            repositoryImpl.newAnonymousUser().let { result ->
                assertTrue(result is Result.Error)
                assertEquals(sqliteException, (result as Result.Error).exception)
            }
        }
    }

    @Test
    fun newUserWithEmailAndNameWithNoError() {

        coJustRun { userDao.save(any()) }

        val repositoryImpl = UsersRepositoryImpl(userDao)

        runTest {
            assertTrue(repositoryImpl.newUserWith("Test", "test@spotapp.com") is Result.Success)
        }
    }

    @Test
    fun newUserWithEmailAndNameWithError() {

        val sqliteException = SQLiteException("an SQLite exception was thrown")
        coEvery { userDao.save(any()) } throws sqliteException

        val repositoryImpl = UsersRepositoryImpl(userDao)

        runTest {
            repositoryImpl.newUserWith("Test", "test@spotapp.com").let { result ->
                assertTrue(result is Result.Error)
                assertEquals(sqliteException, (result as Result.Error).exception)
            }
        }

    }
}