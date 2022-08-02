package com.example.dagger2.data

import com.example.dagger2.data.local.dao.UserDao
import com.example.dagger2.data.model.User
import com.example.dagger2.data.remote.UserRemoteDataSource
import com.example.dagger2.utils.resultLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val dao: UserDao,
    private val remote: UserRemoteDataSource,
    @Named("IO") private val io: CoroutineDispatcher = Dispatchers.IO
) {

    fun observeUser() = resultLiveData(
        databaseQuery = { dao.loadAllUsers() },
        networkCall = { remote.getAllUsers() },
        saveCallResult = { dao.saveAll(it) },
        io = io
    )

    suspend fun updateFavoriteUser(user: User) = dao.updateUser(user)

    suspend fun observeUserById(id: String) = dao.loadUserById(id)
}