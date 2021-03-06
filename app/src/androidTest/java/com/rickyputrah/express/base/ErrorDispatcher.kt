package com.rickyputrah.express.base

import com.squareup.okhttp.mockwebserver.Dispatcher
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.RecordedRequest
import com.squareup.okhttp.mockwebserver.SocketPolicy
import java.net.HttpURLConnection

class ErrorDispatcher(private val errorType: ErrorType) : Dispatcher() {
    override fun dispatch(request: RecordedRequest?): MockResponse {
        return when (errorType) {
            ErrorType.HTTP_EXCEPTION_403 -> MockResponse().setResponseCode(HttpURLConnection.HTTP_FORBIDDEN)
            ErrorType.HTTP_EXCEPTION_404 -> MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
            ErrorType.HTTP_EXCEPTION_TIME_OUT -> MockResponse().apply {
                socketPolicy = SocketPolicy.NO_RESPONSE
            }

        }

    }
}

enum class ErrorType {
    HTTP_EXCEPTION_403,
    HTTP_EXCEPTION_404,
    HTTP_EXCEPTION_TIME_OUT,
}