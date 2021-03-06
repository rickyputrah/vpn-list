package com.rickyputrah.express.base

import android.net.Uri
import com.squareup.okhttp.mockwebserver.Dispatcher
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.RecordedRequest
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.nio.charset.StandardCharsets

class SuccessDispatcher : Dispatcher() {
    private val responseFilesByPath: Map<String, String> = mapOf(
        "/locations" to getXMlFile("list_location.xml")
    )

    override fun dispatch(request: RecordedRequest?): MockResponse {
        val errorResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        val pathWithoutQueryParams = Uri.parse(request?.path).path ?: return errorResponse
        val responseFile = responseFilesByPath[pathWithoutQueryParams]

        return if (responseFile != null) {
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(responseFile)
        } else {
            errorResponse
        }
    }

    private fun getXMlFile(fileName: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)

        if (inputStream != null) {
            val result = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var length: Int

            while (inputStream.read(buffer).also { length = it } != -1) {
                result.write(buffer, 0, length)
            }

            return result.toString(StandardCharsets.UTF_8.name())
        } else return ""
    }
}
