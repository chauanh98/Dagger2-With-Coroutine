package com.example.dagger2.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dagger2.data.UserRepository
import com.example.dagger2.data.model.User
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named
import com.example.dagger2.common.Result

class DetailViewModel @Inject constructor(
    private val repository: UserRepository,
    @Named("IO") private val io: CoroutineDispatcher,
    @Named("MAIN") private val main: CoroutineDispatcher
) : ViewModel() {

    private val _user = MutableLiveData<Result<User>>()
    val user: LiveData<Result<User>> get() = _user

    private fun setResultUser(result: Result<User>) {
        _user.postValue(result)
    }

    private val _isFavorite = MutableLiveData<Result<Boolean>>()
    val favorite: LiveData<Result<Boolean>> get() = _isFavorite

    private fun setResultFavorite(result: Result<Boolean>) {
        _isFavorite.postValue(result)
    }

    internal fun observeUserById(id: String) {
        viewModelScope.launch(main) {
            try {
                setResultUser(Result.loading())
                delay(1_500)
                val result = async(context = io) {
                    repository.observeUserById(id)
                }
                setResultUser(Result.success(result.await()))
            } catch (e: Throwable) {
                setResultUser(Result.error(e.message ?: "Unknown error"))
            }
        }
    }

    internal fun updateFavorite(user: User) {
        viewModelScope.launch(main) {
            try {
                setResultFavorite(Result.loading())
                delay(1_500)
                val result = async(context = io) {
                    repository.updateFavoriteUser(user)
                }
                result.await()
                setResultFavorite(Result.success(user.isFavorite))

            } catch (e: Throwable) {
                setResultFavorite(Result.error(e.message ?: "Unknown error"))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}