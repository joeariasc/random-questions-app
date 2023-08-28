package com.spotapp.mobile.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.spotapp.mobile.data.sources.database.AppDatabase
import com.spotapp.mobile.data.sources.database.users.UserDao
import com.spotapp.mobile.data.sources.database.users.UserDto
import com.spotapp.mobile.data.sources.database.users.UserInfoDto
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
class UserDaoTest {

    private lateinit var userDao: UserDao
    private lateinit var db: AppDatabase

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val user = UserDto(
            userInfo = UserInfoDto(
                email = "some@email.com",
                name = "Test"
            )
        )

        runTest {
            userDao.save(user)

            val allUsers = userDao.findAll()
            assertTrue(allUsers.isNotEmpty())
            assertEquals(user, allUsers[0])
        }
    }
}
