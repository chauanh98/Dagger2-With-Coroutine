package com.example.dagger2.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import com.example.dagger2.common.Result

fun <L, R> resultLiveData(
    databaseQuery: suspend () -> LiveData<L>,
    networkCall: suspend () -> Result<R>,
    saveCallResult: suspend (R) -> Unit,
    io: CoroutineDispatcher
): LiveData<Result<L>> =
    liveData(io) {
        emit(Result.loading())
        delay(1_500)
        val source = databaseQuery.invoke().map {
            Result.success(it)
        }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Result.Status.SUCCESS) {
            responseStatus.data?.let { saveCallResult(it) }
        } else if (responseStatus.status == Result.Status.ERROR) {
            if (responseStatus.message != null) {
                emit(Result.error(responseStatus.message))
            }
            emitSource(source)
        }
    }