package com.example.dagger2.data.remote

import com.example.dagger2.base.BaseDataSource
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val service: UserService) :
    BaseDataSource() {

    suspend fun getAllUsers() = getResult {
        service.getAllUsers()
    }
}