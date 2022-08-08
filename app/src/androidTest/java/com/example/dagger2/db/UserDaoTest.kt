package com.example.dagger2.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dagger2.data.local.UserDatabase
import com.example.dagger2.data.local.dao.UserDao
import com.example.dagger2.data.model.User
import com.example.dagger2.getOrAwaitValue
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: UserDao
    private lateinit var database: UserDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UserDatabase::class.java
        ).build()
        dao = database.userDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun saveUsersList(): Unit = runBlocking {
        val users: List<User> = listOf(
            User(
                "1", "alex", "a", "male", 1,
                "", "", "", 10, 1000, true
            ),
            User(
                "2", "alex", "b", "male", 2,
                "", "", "", 10, 1000, true
            ),
            User(
                "3", "alex", "c", "male", 3,
                "", "", "", 10, 1000, true
            )
        )
        dao.saveAll(users)
        val getAllUser = dao.loadAllUsers().getOrAwaitValue()
        Truth.assertThat(getAllUser).isEqualTo(users)
    }
}