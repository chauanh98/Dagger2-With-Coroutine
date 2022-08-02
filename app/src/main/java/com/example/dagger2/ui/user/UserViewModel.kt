package com.example.dagger2.ui.user

import androidx.lifecycle.ViewModel
import com.example.dagger2.data.UserRepository
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    val user by lazy {
        repository.observeUser()
    }
}