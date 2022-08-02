package com.example.dagger2.data.remote

import com.example.dagger2.data.model.User
import retrofit2.Response
import retrofit2.http.GET

interface UserService {
    companion object {
        const val BASE_URL = "https://xapi-player.herokuapp.com/"
    }

    @GET("api/v1/player/")
    suspend fun getAllUsers(): Response<List<User>>
}