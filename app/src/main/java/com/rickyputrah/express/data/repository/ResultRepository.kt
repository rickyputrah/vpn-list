package com.rickyputrah.express.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.SocketTimeoutException

sealed class ResultRepository<out T : Any> {

    data class Success<out T : Any>(val data: T) : ResultRepository<T>()

    sealed class Error : ResultRepository<Nothing>() {
        object ConnectionTimeout : Error()
        object UnknownError : Error()
        data class HttpException(val errorMessage: String, val errorCode: Int) : Error()
    }
}

suspend fun <T : Any> handleRequest(requestFunc: suspend () -> T): ResultRepository<T> {
    return withContext(Dispatchers.IO) {
        try {
            ResultRepository.Success(requestFunc.invoke())
        } catch (ex: Throwable) {
            return@withContext when {
                (ex is SocketTimeoutException) -> ResultRepository.Error.ConnectionTimeout
                (ex is HttpException) -> ResultRepository.Error.HttpException(
                    ex.message(),
                    ex.code()
                )
                else -> ResultRepository.Error.UnknownError
            }
        }
    }
}


