package com.spotapp.mobile.data.repository.impl

import android.database.sqlite.SQLiteException
import com.spotapp.mobile.data.Result
import com.spotapp.mobile.data.repository.UsersRepository
import com.spotapp.mobile.data.sources.database.users.UserDao
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UsersRepositoryTest {

    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        userDao = mockk()
    }

    @Test
    fun newAnonymousUserWithNoError() {
        coJustRun { userDao.save(any()) }

        val usersRepository = UsersRepository(userDao)

        runTest {
            assertTrue(usersRepository.newAnonymousUser() is Result.Success)
        }
    }

    @Test
    fun newAnonymousUserWithError() {
        val sqliteException = SQLiteException("an SQLite exception was thrown")
        coEvery { userDao.save(any()) } throws sqliteException

        val usersRepository = UsersRepository(userDao)

        runTest {
            usersRepository.newAnonymousUser().let { result ->
                assertTrue(result is Result.Error)
                assertEquals(sqliteException, (result as Result.Error).exception)
            }
        }
    }

    @Test
    fun newUserWithEmailAndNameWithNoError() {
        coJustRun { userDao.save(any()) }

        val usersRepository = UsersRepository(userDao)

        runTest {
            assertTrue(usersRepository.newUserWith("Test", "test@spotapp.com") is Result.Success)
        }
    }

    @Test
    fun newUserWithEmailAndNameWithError() {
        val sqliteException = SQLiteException("an SQLite exception was thrown")
        coEvery { userDao.save(any()) } throws sqliteException

        val usersRepository = UsersRepository(userDao)

        runTest {
            usersRepository.newUserWith("Test", "test@spotapp.com").let { result ->
                assertTrue(result is Result.Error)
                assertEquals(sqliteException, (result as Result.Error).exception)
            }
        }
    }
}
