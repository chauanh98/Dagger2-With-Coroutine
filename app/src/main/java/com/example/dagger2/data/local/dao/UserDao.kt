package com.example.dagger2.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.dagger2.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(uer: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveAll(users: List<User>)

    @Query("SELECT * FROM users")
    fun loadAllUsers(): LiveData<List<User>>

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun loadUserById(id: String): User
}