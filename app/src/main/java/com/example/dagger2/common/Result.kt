package com.example.dagger2.common

data class Result<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Result<T> =
            Result(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(message: String, data: T? = null): Result<T> =
            Result(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T? = null): Result<T> =
            Result(status = Status.LOADING, data = data, message = null)
    }
}